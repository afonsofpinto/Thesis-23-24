package tecnico.com;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Consumer {

    private KafkaConsumer<String, String> consumer;
    private Properties properties;
    private String dw_aws_hostname;

    private final String dw_aws_user = "root";
    private final String dw_aws_password = "password";
    private final String dw_aws_database = "bank_dw";
    private final String kafka_aws_topic = "transactions";

    public Consumer(String kafka_instance_hostname, String dw_instance_hostname) {
        dw_aws_hostname = dw_instance_hostname;
        properties = new Properties();
        properties.put("bootstrap.servers", kafka_instance_hostname);
        properties.put("group.id", "transactions_consume");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("enable.auto.commit", "false"); // Disable auto-commit
        consumer = new KafkaConsumer<>(properties);
    }

    public void receiveMessage() {
        String jdbcUrl = "jdbc:mysql://" + dw_aws_hostname + ":3306/" + dw_aws_database + "?useSSL=false";
        consumer.subscribe(Collections.singletonList(kafka_aws_topic));

        while (true) {
            boolean connectionEstablished = false;
            Connection connection = null;
            try {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                if (!records.isEmpty()) {
                    while (connection == null) {
                        try {
                            connection = DriverManager.getConnection(jdbcUrl, dw_aws_user, dw_aws_password);
                            connectionEstablished = true;
                        } catch (SQLException e) {
                            System.out.println("Error connecting to MySQL database. Retrying...");
                            Thread.sleep(5000); // Wait for 5 seconds before retrying
                        }
                    }
                }
                if (connectionEstablished) {
                    for (ConsumerRecord<String, String> record : records) {
                        System.out.printf("Consumed record with key %s and value %s%n", record.key(), record.value());
                        JSONObject jsonMessage = new JSONObject(record.value());
                        insertData(jsonMessage, connection);

                        // Commit the offset after processing the message
                        Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();
                        offsets.put(new TopicPartition(record.topic(), record.partition()), 
                                    new OffsetAndMetadata(record.offset() + 1));
                        consumer.commitSync(offsets);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static void insertData(JSONObject json, Connection connection) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        String transactionDate = json.getString("DATE"); 
        LocalDateTime extractedDateTime = LocalDateTime.parse(transactionDate, formatter);
        DateTimeFormatter newFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = extractedDateTime.format(newFormatter);
        Date sqlDate = Date.valueOf(formattedDate);
        int timeId = getTimeId(sqlDate, connection);
        String sql = "INSERT INTO fact_bank (TIME_ID, CLIENT_SENDER_ID, CLIENT_RECEIVER_ID, CONTRACT_ID, AMOUNT, TIME_STAMP) VALUES (?, ?, ?, ?, ?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, timeId);
            statement.setInt(2, json.getInt("CLIENT_SENDER_ID"));
            statement.setInt(3, json.getInt("CLIENT_RECEIVER_ID"));
            statement.setInt(4, json.getInt("CONTRACT_ID"));
            statement.setBigDecimal(5, json.getBigDecimal("AMOUNT"));
            statement.setString(6, transactionDate);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Message: " + e.getMessage());
            SQLException nextException = e.getNextException();
            while (nextException != null) {
                System.out.println("Next SQLState: " + nextException.getSQLState());
                System.out.println("Next Error Code: " + nextException.getErrorCode());
                System.out.println("Next Message: " + nextException.getMessage());
                nextException = nextException.getNextException();
            }
            throw new RuntimeException("Impossible to execute statement to the AWS DataWarehouse: " + e.getMessage());
        }
    }

    private static int getTimeId(Date date, Connection connection) {
        String query = "SELECT TIME_ID FROM dim_time WHERE DATE_ = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, date);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("TIME_ID");
                } else {
                    throw new RuntimeException("No TIME_ID found for the given date: " + date.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while fetching TIME_ID for date: " + date.toString(), e);
        }
    }
}
