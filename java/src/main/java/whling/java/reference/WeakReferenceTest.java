package whling.java.reference;

import java.lang.ref.WeakReference;


/**
 * 生命周期很短，不论当前内存是否充足，都只能存活到下一次垃圾收集之前
 */
public class WeakReferenceTest {

    public static void main(String[] args) {
        WeakReference<String> weakReference = new WeakReference<String>(new String("hello world"));
        System.gc();
        if (weakReference.get() == null) {
            System.out.println("weakReference已经被GC回收");
        }
    }
}
