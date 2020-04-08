package whling.knowledge.alogrithm.lfu;

import java.util.*;

/**
 * 不是线程安全的
 * @param <K>
 * @param <V>
 */
public class LFU<K, V> {

    /**
     * 总容量
     */
    private int capacity;

    /**
     * 所有的node节点
     */
    private Map<K, Node> caches;

    /**
     * 构造方法
     *
     * @param size
     */
    public LFU(int size) {
        this.capacity = size;
        caches = new LinkedHashMap<K, Node>(size);
//        caches = new HashMap<K, Node>(size);
    }


    /**
     * 添加元素
     *
     * @param key
     * @param value
     */
    public void put(K key, V value) {
        Node node = caches.get(key);
        //如果新元素
        if (node == null) {
            //如果超过元素容纳量
            if (caches.size() >= capacity) {
                //移除count计数最小的那个key
                K leastKey = removeLeastCount();
                caches.remove(leastKey);
            }
            //创建新节点
            node = new Node(key, value, System.nanoTime(), 1);
            caches.put(key, node);
        } else {
            //已经存在的元素覆盖旧值
            node.value = value;
            node.setCount(node.getCount() + 1);
            node.setTime(System.nanoTime());
        }
        sort();
    }

    /**
     * 排序
     */
    private void sort() {

        List<Map.Entry<K, Node>> list = new ArrayList<>(caches.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, Node>>() {
            @Override
            public int compare(Map.Entry<K, Node> o1, Map.Entry<K, Node> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        caches.clear();
        for (Map.Entry<K, Node> kNodeEntry : list) {
            caches.put(kNodeEntry.getKey(), kNodeEntry.getValue());
        }
    }

    /**
     * 移除统计数或者时间比较最小的那个
     *
     * @return
     */
    private K removeLeastCount() {
        Collection<Node> values = caches.values();
        Node min = Collections.min(values);
        return (K) min.getKey();

    }


    /**
     * 获取元素
     *
     * @param key
     * @return
     */
    public V get(K key) {
        Node node = caches.get(key);
        if (node != null) {
            node.setCount(node.getCount() + 1);
            node.setTime(System.nanoTime());
            sort();
            return (V) node.value;
        }
        return null;
    }

    public static void main(String[] args) {

        LFU<Integer, String> lruCache = new LFU<>(5);
        lruCache.put(1, "A");
        lruCache.put(2, "B");
        lruCache.put(3, "C");
        lruCache.put(4, "D");
        lruCache.put(5, "E");
        lruCache.put(6, "F");
        lruCache.get(2);
        lruCache.get(2);
        lruCache.get(3);
        lruCache.get(6);
        //重新put节点3
        lruCache.put(3, "C");
        final Map<Integer, Node> caches = (Map<Integer, Node>) lruCache.caches;
        for (Map.Entry<Integer, Node> nodeEntry : caches.entrySet()) {
            System.out.println(nodeEntry.getValue().value);
        }
    }
}