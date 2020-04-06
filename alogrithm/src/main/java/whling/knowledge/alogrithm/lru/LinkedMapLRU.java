package whling.knowledge.alogrithm.lru;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 该类是线程安全的带有容量限制的LinkedHashMap
 * 超容量的元素采用LRU方式删除
 * 线程安全仅限于已经覆盖的几个方法，若有其他需要可自行添加
 * 迭代操作不保证数据的一致性，即使remove方法是线程安全的也会抛ConcurrentModificationException
 */
public class LinkedMapLRU<K, V> extends LinkedHashMap<K, V> {
    private static final long serialVersionUID = -7911712053305433954L;

    private int capacity;
    private final ReentrantLock lock = new ReentrantLock();

    public LinkedMapLRU(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    public V put(K key, V value) {
        try {
            lock.lock();
            return super.put(key, value);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public V get(Object key) {
        try {
            lock.lock();
            return super.get(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public V remove(Object key) {
        try {
            lock.lock();
            return super.remove(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        if (size() > capacity) {
            System.out.println(eldest.getKey() + ":" + eldest.getValue());
            return true;
        }
        return false;
    }

    public void setMaxSize(int size) {
        this.capacity = size;
    }


    public static void main(String[] args) {
        LinkedMapLRU<Integer, Integer> LRU = new LinkedMapLRU<Integer, Integer>(3);
        LRU.put(3, 1);
        LRU.put(6, 2);
        LRU.put(9, 3);
        LRU.get(3); //key=3移到顶部
        LRU.put(12, 4);
        LRU.put(15, 5);
        System.out.println("=============");
        for (Map.Entry<Integer, Integer> entry : LRU.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

}