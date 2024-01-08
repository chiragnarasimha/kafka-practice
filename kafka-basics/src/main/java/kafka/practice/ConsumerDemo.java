package kafka.practice;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerDemo {
    public static final Logger logger = LoggerFactory.getLogger(ConsumerDemo.class.getSimpleName());

    public static void main(String[] args) {
        logger.info("I am a Kafka Consumer");
        final String GROUP_ID = "my-java-application";
        final String TOPIC_NAME = "demo_java";


        // Create the consumer properties
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");


        // Set the consumer properties
        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", StringDeserializer.class.getName());
        properties.setProperty("group.id", GROUP_ID);
        properties.setProperty("auto.offset.reset", "earliest");

        // Create the consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        // Subscribe to the topic
        consumer.subscribe(Arrays.asList(TOPIC_NAME));

        // poll for data
        while (true) {
//            logger.info("Polling");
            ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record : consumerRecords) {
                logger.info("Key: " + record.key() + " | Value: " + record.value() + " | Partition: " + record.partition() + " | Offset: " + record.offset());
            }
        }


    }
}
