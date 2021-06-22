package aws.example.s3;

import java.util.Scanner;

public class Example {

    public static void main(String[] args) {

        SaveFile saveFile = new SaveFile();
        Scanner scanner = new Scanner(System.in);

        int opt = Option();
        while (opt != 2) {
            if (opt == 1) {
                System.out.println();
                System.out.print("Nombre del archivo: ");
                String key_name = scanner.nextLine();
                saveFile.DownloadFile(key_name);
                opt = Option();
            } else {
                System.out.println("Opción inválida");
                opt = Option();
            }
        }
    }

    private static int Option() {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("########################");
        System.out.println("Menú");
        System.out.println("1. Descargar archivo");
        System.out.println("2. Salir");
        System.out.println("########################");
        System.out.println();

        System.out.print("Ingresar opción: ");
        String opt = scanner.nextLine();
        try {
            return Integer.valueOf(opt);
        } catch(NumberFormatException e) {
            return 0;
        }
    }


}

