package aws.example.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

import java.io.*;
import java.util.UUID;

public class SaveFile {

    private static void SendMessageSQS(String name_file) {
        String queue_url = "https://sqs.us-west-1.amazonaws.com/251799574311/prueba.fifo";
        AmazonSQS sqs = AmazonSQSClientBuilder.standard().withRegion("us-west-1").build();
        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(queue_url)
                .withMessageBody(name_file)
                .withMessageGroupId(UUID.randomUUID().toString())
                .withMessageDeduplicationId(UUID.randomUUID().toString());
        SendMessageResult msg = sqs.sendMessage(send_msg_request);
        System.out.println(msg);
    }

    public void DownloadFile(String key_name) {
        String bucket_name = "prueba-uno-bucket";
        System.out.format("Downloading %s from S3 bucket %s...\n", key_name, bucket_name);
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_WEST_1).build();
        try {
            if (s3.doesObjectExist(bucket_name, key_name)) {
                S3Object o = s3.getObject(bucket_name, key_name);
                S3ObjectInputStream s3is = o.getObjectContent();
                File file = new File("C:\\Users\\lbolanos\\Documents\\Download\\" + key_name);
                FileOutputStream fos = new FileOutputStream(file);
                byte[] read_buf = new byte[1024];
                int read_len = 0;
                while ((read_len = s3is.read(read_buf)) > 0) {
                    fos.write(read_buf, 0, read_len);
                }
                s3is.close();
                fos.close();

                System.out.println("Leyendo archivo...");
                String row = "";
                BufferedReader csvReader = new BufferedReader(new FileReader(file));
                while ((row = csvReader.readLine()) != null) {
                    System.out.println("- " + row);
                }
                csvReader.close();
                SendMessageSQS(key_name);
                System.out.println("Archivo descargado y enviado a SQS");
            } else {
                System.out.println("La clave especificada no existe.");
            }
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
