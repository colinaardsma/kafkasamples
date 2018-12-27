package helpers;

import org.apache.kafka.common.TopicPartition;

import java.util.ArrayList;
import java.util.Arrays;

public class Constants {
    public static String myTopic = "my_topic";
    public static String myNewTopic = "my_new_topic";
    public static ArrayList<String> topics = new ArrayList<>(Arrays.asList(myTopic, myNewTopic));

    public static TopicPartition myTopicPartition = new TopicPartition(myTopic, 0);
    public static TopicPartition myNewTopicPartition = new TopicPartition(myNewTopic, 2);
    public static ArrayList<TopicPartition> partitions = new ArrayList<>(Arrays.asList(myTopicPartition, myNewTopicPartition));

    public static String serverAddress = "10.211.55.5";
    public static String serverList = serverAddress + ":9092," + serverAddress + ":9093," + serverAddress + ":9094";
}
