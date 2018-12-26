package helpers;

import java.util.ArrayList;
import java.util.Arrays;

public class Constants {
    public static String myTopic = "my_topic";
    public static String myNewTopic = "my_new_topic";
    public static ArrayList<String> topics = new ArrayList<>(Arrays.asList(myTopic, myNewTopic));

    public static String serverAddress = "10.211.55.5";
    public static String serverList = serverAddress + ":9092," + serverAddress + ":9093," + serverAddress + ":9094";
}
