package whling.java.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;

/**
 * 这种引用类型不会影响对象的生命周期，所持有的引用就跟没持有一样，随时都能被GC回收。需要注意的是，在使用虚引用时，必须和引用队列关联使用。
 * 在对象的垃圾回收过程中，如果GC发现一个对象还存在虚引用，则会把这个虚引用加入到与之关联的引用队列中。程序可以通过判断引用队列中是否已经加入了虚引用，
 * 来了解被引用的对象是否将要被垃圾回收。
 *
 * 在jvm中的应用：使用堆外内存被回收的时候使用
 *
 * DirectByteBuffer对象一旦在内存中没有了引用，那么代表该对象的直接内存需要进行回收，这时，就会在DirectByteBuffer关联的queue中添加该区域的标记，gc 垃圾收集器中
 * 有专门负责回收堆外内存的线程，会去扫描该队列
 */
public class PhantomReferenceTest {


    static ReferenceQueue<List<byte[]>> queue = new ReferenceQueue<>();

    static List<byte[]> list = new ArrayList<>();

    static PhantomReference<List<byte[]>> phantomReference = new PhantomReference<>(list, queue);


    static void process() {
        list.add(new byte[1024 * 500]);

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }

        System.out.println(phantomReference.get());
    }

    public static void main(String[] args) {

        new Thread(() -> {
            process();
        }).start();


        new Thread(() -> {
            while (true) {
                Reference<? extends List<byte[]>> r = queue.poll();
                if (r != null) {
                    System.out.println("r:" + r);
                }

            }
        }).start();

        try {
            System.gc();
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
        }
    }
}
