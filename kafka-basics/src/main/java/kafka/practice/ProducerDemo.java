package kafka.practice;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProducerDemo {
    public static final Logger logger = LoggerFactory.getLogger(ProducerDemo.class.getSimpleName());

    public static void main(String[] args) {
        logger.info("I am a Kafka Producer");
        // Create the producer properties
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");

        // Set the producer properties
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());

        // Create the producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        // Create the producer record and send data

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH'h':mm'm':ss's':SSSS'ms'");

        final String TOPIC_NAME = "demo_java";


        for (int i = 0; i < 10; i++) {
            // Get the current date and time
            LocalDateTime now = LocalDateTime.now();
            String formattedDateTime = now.format(formatter);

            String key = i + ". " + formattedDateTime;
            String value = key + " Hello from Java";

            // Create a producer record with the current date and time


            if (i == 0) {
                value = "\n\n" + key + " New Run";
            }

            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC_NAME, key, value);

            // Send the record
            producer.send(producerRecord, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e == null) {
                        logger.info("Received New Metadata\n" +
                                "Topic: " + recordMetadata.topic() + "\n" +
                                "Key: " + key+ "\n" +
                                "Partition: " + recordMetadata.partition() + "\n" +
                                "Offset: " + recordMetadata.offset() + "\n" +
                                "Timestamp: " + recordMetadata.timestamp() + "\n"
                        );
                    } else {
                        logger.error("Error while producing record\n", e);
                    }
                }
            });
        }

        // Tell the producer to send all data and block until done --> synchronous
        producer.flush();

        // Close the producer
        producer.close();


    }
}
