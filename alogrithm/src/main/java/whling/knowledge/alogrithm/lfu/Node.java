package whling.knowledge.alogrithm.lfu;

/**
 * 节点
 */
public class Node implements Comparable<Node> {
    //键
    Object key;
    //值
    Object value;
    /**
     * 访问时间
     */
    long time;

    /**
     * 访问次数
     */
    int count;

    public Node(Object key, Object value, long time, int count) {
        this.key = key;
        this.value = value;
        this.time = time;
        this.count = count;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int compareTo(Node o) {
        int compare = Integer.compare(this.count, o.count);
        //在数目相同的情况下 比较时间
        if (compare == 0) {
            return Long.compare(this.time, o.time);
        }
        return compare;
    }
}