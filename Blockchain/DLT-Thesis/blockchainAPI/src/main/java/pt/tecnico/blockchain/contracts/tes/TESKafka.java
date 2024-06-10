package pt.tecnico.blockchain.contracts.tes;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class TESKafka {

    private static KafkaProducer<String, String> producer;
    private static Properties properties;
    private static final String kafka_topic = "transactions";

    private static final String filePath = "../blockchainAPI/src/main/java/pt/tecnico/blockchain/contracts/tes/kafka_hostnames.tmp";

    private static String kafka_full_hostname;


    static {
        try{
            kafka_full_hostname = new String(Files.readAllBytes(Paths.get(filePath))).trim() + ":9092";
        }catch (IOException e) {
            System.err.println("Error reading the file: ");
        }
        properties = new Properties();
        properties.put("bootstrap.servers", kafka_full_hostname);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(properties);
    }

    public static void sendMessage(Integer sender_id,Integer receiver_id,Integer contract_id,Integer amount) {
        JSONObject jsonMessage = new JSONObject();
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        String date = currentDateTime.format(formatter);
        jsonMessage.put("DATE", date);
        jsonMessage.put("CLIENT_SENDER_ID", sender_id);
        jsonMessage.put("CLIENT_RECEIVER_ID", receiver_id);
        jsonMessage.put("CONTRACT_ID", contract_id);
        jsonMessage.put("AMOUNT", amount);
        String key = "jsonKey";
        String value = jsonMessage.toString();

        ProducerRecord<String, String> record = new ProducerRecord<>(kafka_topic, key, value);
        producer.send(record);
        System.out.println("JSON message sent successfully");
    }
}