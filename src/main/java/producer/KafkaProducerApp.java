
package producer;

import helpers.Constants;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import java.util.*;

public class KafkaProducerApp {

    public static void main(String[] args) {
        String key = null;
        String val = null;

        // "Blah" "Made Through Java"
        if (args.length > 0) {
            key = args[0];
            val = args.length > 1 ? args[1] : null;
        }
        else {
            Scanner reader = new Scanner(System.in);
            System.out.println("Enter key (enter if null): ");
            key = reader.nextLine();
            while (val == null) {
                System.out.println("Enter value (required): ");
                val = reader.nextLine();
            }
        }

        KafkaProducer myProducer = CreateKafkaProducer();
        ProducerRecord myMessage = CreateProducerRecord(key, val);
        RunKafkaProduer(myProducer, myMessage);
    }

    private static KafkaProducer CreateKafkaProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.ACKS_CONFIG, "1");
        props.put(ProducerConfig.RETRIES_CONFIG, "3");
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.serverList);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return new KafkaProducer(props);
    }

    private static void RunKafkaProduer(KafkaProducer myProducer, ProducerRecord myMessage) {
        try {
            System.out.println("Trying to send message...");
            myProducer.send(myMessage).get();
            System.out.println("Message sent.");
        }
        catch (Exception e) {
            System.out.println("PRODUCER FAILURE!");
            System.out.println(e);
        }
        finally {
            myProducer.close();
        }
    }

    private static ProducerRecord CreateProducerRecord(String key, String val) {
        return new ProducerRecord(Constants.myTopic, key, val);
    }
}
