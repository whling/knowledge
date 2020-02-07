package whling.knowledge.alogrithm.loadbance.roundrobin;

import whling.knowledge.alogrithm.loadbance.IpServer;

import java.util.List;

public class SimpleRoundRobin {

    public static int count = 0;

    public static void main(String[] args) {
        List<String> ipList = IpServer.IP_LIST;
        for (int i = 0; i < 100; i++) {
            int pos = count % ipList.size();
            System.out.println(ipList.get(pos));

            count++;
            if (count == 50) {
                count = 0;
            }
        }
    }
}
