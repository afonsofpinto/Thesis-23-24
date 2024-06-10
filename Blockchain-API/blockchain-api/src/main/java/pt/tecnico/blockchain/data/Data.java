package pt.tecnico.blockchain.data;

import jakarta.annotation.PostConstruct;

import java.util.HashMap;
import java.util.Map;


public class Data {
    private static Map<String, Integer> clientIdMap = new HashMap<>();
    private static Map<String, Integer> clientPortMap = new HashMap<>();

    static {
        // Call init() method when the class is loaded
        init();
    }

    @PostConstruct
    public static void init() {
        clientIdMap.put("afonso", 4);
        clientIdMap.put("sidnei", 5);
        clientIdMap.put("carlota", 6);
        clientPortMap.put("afonso", 10024);
        clientPortMap.put("sidnei", 10025);
        clientPortMap.put("carlota", 10026);
    }

    public static int getClientPort(String client_name) {
        return clientPortMap.get(client_name);
    }

    public static int getClientId(String client_name) {
        return clientIdMap.get(client_name);
    }

    public static boolean containsKey(String client_name) {
        return clientPortMap.containsKey(client_name) && clientIdMap.containsKey(client_name);
    }
}

