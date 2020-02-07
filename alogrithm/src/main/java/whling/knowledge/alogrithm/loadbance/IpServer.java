package whling.knowledge.alogrithm.loadbance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IpServer {

    public static final List<String> IP_LIST = new ArrayList<>();

    public static final Map<String, Integer> IP_WIGHT_MAP = new HashMap<>();

    static {
        IP_LIST.add("B");
        IP_LIST.add("C");
        IP_LIST.add("D");

        IP_WIGHT_MAP.put("A", 5);
        IP_WIGHT_MAP.put("B", 1);
        IP_WIGHT_MAP.put("C", 1);
    }

}
