package whling.knowledge.alogrithm.loadbance.hash;

import whling.knowledge.alogrithm.loadbance.IpServer;

public class HashLoadBalance {

    public static void main(String[] args) {
        int size = IpServer.IP_LIST.size();
        for (int i = 0; i < 100; i++) {
            String req = "requestId_" + i;
            System.out.println(IpServer.IP_LIST.get(req.hashCode() % size));
        }
    }
}
