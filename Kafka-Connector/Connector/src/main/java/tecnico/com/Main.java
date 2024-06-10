package tecnico.com;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Error: Two .tmp files required for reading the <hostname_file_paths>");
            System.exit(1);
        }

        String kafkaHostnameFilePath = args[0];
        String datawarehouseHostnameFilePath = args[1];
        String kafkaAWShostname = readHostnameFromFile(kafkaHostnameFilePath);
        String datawarehouseAWShostname = readHostnameFromFile(datawarehouseHostnameFilePath);

        if (kafkaAWShostname == null || datawarehouseAWShostname == null) {
            System.err.println("Failed to read hostname from one or both files.");
            System.exit(1);
        }

        String kafka_full_hostname = kafkaAWShostname + ":9092";
        Consumer consumer = new Consumer(kafka_full_hostname, datawarehouseAWShostname);
        consumer.receiveMessage();

    }

    private static String readHostnameFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

