package whling.knowledge.alogrithm.loadbance.random;

import whling.knowledge.alogrithm.loadbance.IpServer;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class WeightRandom {

    public static void main(String[] args) {
        Map<String, Integer> ipWightMap = IpServer.IP_WIGHT_MAP;

//        weightRandom1(ipWightMap);
        weightRandom2(ipWightMap);
    }

    /**
     * ipList会随着权重值的大小而变化
     *
     * @param ipWightMap
     */
    private static void weightRandom1(Map<String, Integer> ipWightMap) {
        ArrayList<String> newIpList = new ArrayList<>();
        Integer round = 0;
        for (Map.Entry<String, Integer> stringIntegerEntry : ipWightMap.entrySet()) {
            String key = stringIntegerEntry.getKey();
            Integer value = stringIntegerEntry.getValue();
            round += value;
            for (int i = 0; i < value; i++) {
                newIpList.add(key);
            }
        }

        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            System.out.println(newIpList.get(random.nextInt(round)));
        }
    }


    private static void weightRandom2(Map<String, Integer> ipWightMap) {
        Integer totalWeight = ipWightMap.entrySet().stream()
                .map(Map.Entry::getValue)
                .reduce((a, b) -> a + b).get();
        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            int pos = random.nextInt(totalWeight);

            for (Map.Entry<String, Integer> stringIntegerEntry : ipWightMap.entrySet()) {


                Integer weight = stringIntegerEntry.getValue();
                if (pos < weight) {
                    System.out.println(stringIntegerEntry.getKey());
                } else {
                    pos = totalWeight - weight;
                }
            }
        }
    }


}
