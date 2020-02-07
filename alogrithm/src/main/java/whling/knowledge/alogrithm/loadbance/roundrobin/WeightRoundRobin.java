package whling.knowledge.alogrithm.loadbance.roundrobin;

import whling.knowledge.alogrithm.loadbance.IpServer;

import java.util.HashMap;
import java.util.Map;

public class WeightRoundRobin {


    public static int count = 0;

    public static void main(String[] args) {
        weightRoundRobin1(); //这样对于权重大的来说，连续性比较大，不够均匀
        weightRoundRobin2(); //生成类似AABACAA的样子
    }

    private static void weightRoundRobin1() {
        /**
         * 可以基于随机算法的实现方式，只是产生的数，不要随机生成，递增即可
         */
    }

    /**
     * curWeight += weight   max(curWeight)    result  max(curWeight) -= totalWeight
     * 5,1,1                   5                   A       -2,1,1
     * 3,2,2                   3                   A       -4,2,2
     * 1,3,3                   3                   B       1,-4,3
     * 6,-3,4                  6                   A       -1,-3,4
     * 4,-2,5                  5                   C       4,-2,-2
     * 9,-1,-1                 9                   A       2,-1,-1
     * 7,0,0                   7                   A       -1,0,0
     * 4,1,1                   4                   A       -3,1,1
     */
    public static final Map<String, Weight> ipWeightMap = new HashMap<>();

    private static void weightRoundRobin2() {
        for (Map.Entry<String, Integer> stringIntegerEntry : IpServer.IP_WIGHT_MAP.entrySet()) {
            ipWeightMap.put(stringIntegerEntry.getKey(), new Weight().builder().ip(stringIntegerEntry.getKey())
                    .weight(stringIntegerEntry.getValue()).currentWeight(0).build());
        }

        Integer totalWeight = 0;
        for (Map.Entry<String, Weight> stringWeightEntry : ipWeightMap.entrySet()) {
            totalWeight += stringWeightEntry.getValue().getWeight();
        }


        Weight maxWeight = null;
        for (int i = 0; i < 100; i++) {
            for (Weight weight : ipWeightMap.values()) {
                weight.setCurrentWeight(weight.getCurrentWeight() + weight.getWeight());
                if (maxWeight == null
                        || weight.getCurrentWeight() > maxWeight.getCurrentWeight()) {
                    maxWeight = weight;
                }
            }

            System.out.println(maxWeight.getIp());
            maxWeight.setCurrentWeight(maxWeight.getCurrentWeight() - totalWeight);
        }
    }

    public static class Weight {
        public String ip;
        public Integer weight;
        public Integer currentWeight;

        public WeightBuilder builder() {
            return new WeightBuilder();
        }

        public class WeightBuilder {
            public WeightBuilder ip(String ip) {
                Weight.this.setIp(ip);
                return this;
            }

            public WeightBuilder weight(Integer weight) {
                Weight.this.setWeight(weight);
                return this;
            }

            public WeightBuilder currentWeight(Integer currentWeight) {
                Weight.this.setCurrentWeight(currentWeight);
                return this;
            }

            public Weight build() {
                return Weight.this;
            }
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        public Integer getCurrentWeight() {
            return currentWeight;
        }

        public void setCurrentWeight(Integer currentWeight) {
            this.currentWeight = currentWeight;
        }
    }
}
