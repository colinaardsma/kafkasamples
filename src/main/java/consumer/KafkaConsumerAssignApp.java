
package consumer;

import helpers.Constants;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import java.time.Duration;
import java.util.*;

public class KafkaConsumerAssignApp {

    public static void main(String[] args) {
        KafkaConsumer myConsumer = CreateKafkaConsumer();
        RunKafkaConsumer(myConsumer);
    }

    private static KafkaConsumer CreateKafkaConsumer() {
        Properties props = new Properties();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "java-test-consumer");
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.serverList);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        return new KafkaConsumer(props);
    }

    private static void RunKafkaConsumer(KafkaConsumer myConsumer) {
        System.out.println("Topics received. Trying to subscribe to topic...");
        myConsumer.assign(Constants.partitions);

        try (myConsumer) {
            while (true) {
                var records = myConsumer.poll(Duration.ofMillis(1000));
                records.forEach(x -> PrintRecord((ConsumerRecord<String,String>)x));
            }
        } catch (Exception e) {
            System.out.println("CONSUMER FAILURE!");
            System.out.println(e);
        }
    }

    private static void PrintRecord(ConsumerRecord<String, String> record) {
        System.out.println(String.format("Topic: %s, Key: %s, Value: %s, Partition: %d, Offset: %d", record.topic(), record.key(), record.value(), record.partition(), record.offset()));
    }
}
