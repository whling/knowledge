package whling.knowledge.alogrithm.loadbance.hash;

import whling.knowledge.alogrithm.loadbance.IpServer;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 一致性hash算法
 */
public class ConsistenceHash {

    public static final int VITURAL_NODE_COUNT = 100;

    public static void main(String[] args) {
        TreeMap<Integer, String> consistenceHashMap = new TreeMap<>();
        for (String ip : IpServer.IP_LIST) {
            for (int i = 0; i < VITURAL_NODE_COUNT; i++) {
                consistenceHashMap.put((ip + "_" + i).hashCode(), ip);
            }
        }

        for (int i = 0; i < 200; i++) {
            String req = "requestId_" + i;
            SortedMap<Integer, String> sortedMap = consistenceHashMap.tailMap(req.hashCode());
            if (sortedMap.isEmpty()) {
                System.out.println(consistenceHashMap.get(consistenceHashMap.firstKey()));
            } else {
                System.out.println(sortedMap.get(sortedMap.firstKey()));
            }
        }
    }
}
