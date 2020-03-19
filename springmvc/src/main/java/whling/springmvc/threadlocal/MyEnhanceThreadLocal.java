package whling.springmvc.threadlocal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * 使用该内部结构Map<Thread, Map<Integer, Object>>存储线程局部变量的映射关系，而不是Map<Thread, Map<MyEnhanceThreadLocal, Object>>
 *
 *     为什么？
 *
 *     当一个线程结束时，该Map就没有该线程的引用了，那么线程对应的Map<MyEnhanceThreadLocal, Object>数据，也就没有意义了，但是系统中创建的MyEnhanceThreadLocal多为成员变量
 *     一般会有相关对象引用他，那么他就继续存在被引用而不被垃圾回收掉，那么这块Map<MyEnhanceThreadLocal, Object>的数据也就不能被回收了，即使在线程结束之后该Map.Entry还存在于内存中。
 *
 *     俩种解决：
 *      1.使用一个Integer（基本数据类型，并非引用类型）能代替MyEnhanceThreadLocal的标志，确保一一对应
 *      2.使用WeakReference<MyEnhanceThreadLocal<?>> 来包装一层，那么MyEnhanceThreadLocal和Entry就没有必然直接引用关系了，当该Entry没有引用时，那么在gc时，这块区域是可以被回收的。
 *
 *
 *
 * 在理想状态下，哈希函数可以将关键字均匀的分散到数组的不同位置，不会出现两个关键字散列值相同（假设关键字数量小于数组的大小）的情况。但是在实际使用中，经常会出现多个关键字散列值相同的情况（被映射到数组的同一个位置），
 * 我们将这种情况称为散列冲突。为了解决散列冲突，主要采用下面两种方式：
 * •	分离链表法（separate chaining）
 * •	开放定址法（open addressing）
 *
 * ThreadLocalMap 中使用开放地址法来处理散列冲突，而 HashMap 中使用的分离链表法。
 * 之所以采用不同的方式主要是因为：在 ThreadLocalMap 中的散列值分散的十分均匀，很少会出现冲突。并且 ThreadLocalMap 经常需要清除无用的对象，使用纯数组更加方便。
 *
 * 本案例内部使用HashMap实现，即底层还是使用分离链表法
 *
 * @param <T>
 */
public class MyEnhanceThreadLocal<T> {

    private static final int HASH_INCREMENT = 0x61c88647;

    /**
     * 静态
     */
    private static AtomicInteger nextHashCode =
            new AtomicInteger();

    /**
     * 确保每个threadlocal对象对应的值是不一样的,该字段threadLocalHash，是不能被static修饰的
     */
    Integer threadLocalHash = nextHashCode();


    private static int nextHashCode() {
        return nextHashCode.addAndGet(HASH_INCREMENT);
    }


    private static final Map<Thread, Map<Integer, Object>> MAP = new HashMap<>();

    public T initialValue() {
        return null;
    }

    public Map<Integer, Object> getMap() {
        Thread curThread = Thread.currentThread();
        if (!MAP.containsKey(curThread)) {
            Map<Integer, Object> map = new HashMap<>();
            MAP.put(curThread, map);
        }
        return MAP.get(curThread);
    }

    public T get() {
        Map<Integer, Object> map = getMap();
        if (!map.containsKey(threadLocalHash)) {
            map.put(threadLocalHash, initialValue());
        }
        return (T) map.get(threadLocalHash);
    }

    public void set(Object obj) {
        Map<Integer, Object> map = getMap();
        map.put(threadLocalHash, obj);
    }

    public void remove() {
        Map<Integer, Object> map = getMap();
        map.remove(threadLocalHash);
    }


    public static <T> MyEnhanceThreadLocal withInitial(Supplier<T> supplier) {
        return new MySupplierThreadLocal<T>(supplier);
    }

    static class MySupplierThreadLocal<T> extends MyEnhanceThreadLocal<T> {
        private Supplier<T> supplier;

        public MySupplierThreadLocal(Supplier<T> supplier) {
            this.supplier = supplier;
        }

        @Override
        public T initialValue() {
            return supplier.get();
        }
    }


    public static void main(String[] args) {

        MyEnhanceThreadLocal<String> threadLocal1 = MyEnhanceThreadLocal.withInitial(() -> "i am default value");
        MyEnhanceThreadLocal<String> threadLocal2 = MyEnhanceThreadLocal.withInitial(() -> "i am your baby");
        MyEnhanceThreadLocal<String> threadLocal3 = MyEnhanceThreadLocal.withInitial(() -> "i am your baby3");

        try {
            threadLocal1.set("whling");

            System.out.println(threadLocal1.get());
        } finally {
            threadLocal1.remove();

        }
        System.out.println(threadLocal1.get());


        try {
            threadLocal2.set("cc");

            System.out.println(threadLocal2.get());
        } finally {
            threadLocal2.remove();

        }

        threadLocal3.set("hello");
        System.out.println(threadLocal2.get());
        System.out.println(threadLocal1.get());
        System.out.println(threadLocal3.get());

        System.out.println("=================");

        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            set.add(nextHashCode());
        }
        System.out.println(set.size());

    }

}
