package whling.knowledge.alogrithm.loadbance.random;

import whling.knowledge.alogrithm.loadbance.IpServer;

import java.util.List;
import java.util.Random;

public class SimpleRandom {

    public static void main(String[] args) {

        List<String> ipList = IpServer.IP_LIST;
        int size = ipList.size();

        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            System.out.println(ipList.get(random.nextInt(size)));
        }

    }
}
