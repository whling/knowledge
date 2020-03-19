package whling.springmvc.threadlocal;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MyThreadLocal<T> {


    private static final Map<Thread, Map<MyThreadLocal<?>, Object>> MAP = new HashMap<>();


    public T initialValue() {
        return null;
    }

    public Map<MyThreadLocal<?>, Object> getMap() {
        Thread curThread = Thread.currentThread();
        if (!MAP.containsKey(curThread)) {
            Map<MyThreadLocal<?>, Object> map = new HashMap<>();
            MAP.put(curThread, map);
        }
        return MAP.get(curThread);
    }

    public T get() {
        Map<MyThreadLocal<?>, Object> map = getMap();
        if (!map.containsKey(this)) {
            map.put(this, initialValue());
        }
        return (T) map.get(this);
    }

    public void set(Object obj) {
        Map<MyThreadLocal<?>, Object> map = getMap();
        map.put(this, obj);
    }

    public void remove() {
        Map<MyThreadLocal<?>, Object> map = getMap();
        map.remove(this);
    }

    public static <T> MyThreadLocal withInitial(Supplier<T> supplier) {
        return new MySupplierThreadLocal<T>(supplier);
    }

    static class MySupplierThreadLocal<T> extends MyThreadLocal<T> {
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

        MyThreadLocal<String> threadLocal1 = MyThreadLocal.withInitial(() -> "i am default value");
        MyThreadLocal<String> threadLocal2 = MyThreadLocal.withInitial(() -> "i am your baby");

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
        System.out.println(threadLocal2.get());
    }

}
