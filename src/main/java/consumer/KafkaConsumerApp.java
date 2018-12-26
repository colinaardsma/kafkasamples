
package consumer;

import helpers.Constants;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.serialization.StringDeserializer;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class KafkaConsumerApp {

    public static void main(String[] args) {
        KafkaConsumer myConsumer = CreateKafkaConsumer();
        GetAllTopics(myConsumer).forEach(System.out::println);
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

    private static List<List<String>> GetAllTopics(KafkaConsumer myConsumer) {
        System.out.println("Trying to get topics...");
        Map<String, List<PartitionInfo>> listTopics = myConsumer.listTopics();
        return listTopics.values().stream().map(x -> x.stream().map(PartitionInfo::topic).collect(Collectors.toList())).collect(Collectors.toList());
    }

    private static void RunKafkaConsumer(KafkaConsumer myConsumer) {
        System.out.println("Topics received. Trying to subscribe to topic...");
        myConsumer.subscribe(Constants.topics);

        myConsumer.poll(Duration.ofMillis(0));
        myConsumer.seekToBeginning(myConsumer.assignment());
        var existingRecords = myConsumer.poll(Duration.ofMillis(1000));
        existingRecords.forEach(System.out::println);

        try (myConsumer) {
            while (true) {
                var records = myConsumer.poll(Duration.ofMillis(1000));
                records.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("CONSUMER FAILURE!");
            System.out.println(e);
        }
    }
}
