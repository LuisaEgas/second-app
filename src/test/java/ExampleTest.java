import aws.example.s3.SaveFile;
import org.junit.jupiter.api.Test;

public class ExampleTest {

    SaveFile saveFile = new SaveFile();

    @Test
    public void saveFile1() {
        saveFile.DownloadFile("file1.csv");
    }

    @Test
    public void saveFile2() {
        saveFile.DownloadFile("file2.csv");
    }

    @Test
    public void saveFile3() {
        saveFile.DownloadFile("file3.csv");
    }

    @Test
    public void saveFile4() {
        saveFile.DownloadFile("file4.csv");
    }
}
