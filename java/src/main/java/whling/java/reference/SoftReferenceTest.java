package whling.java.reference;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 强引用：默认创建的对象是强引用。在GC Roots可达性分析中，如果对象可达，那么表示还有别的对象引用他，不能被gc回收掉，即时发生OOM
 *
 * 软引用：当JVM内存不足的时候，才会去回收这个引用对象
 * 可以多用于一些缓存场景
 */
public class SoftReferenceTest {


    public static void main(String[] args) {

        List<String> list = new ArrayList<>();

        SoftReference<List<String>> listSoftReference = new SoftReference<List<String>>(list);


        for (int i = 0; i < 1000; i++) {
            list.add("hello 啊 " + i);
        }

        List<String> strings = listSoftReference.get();
        System.out.println(strings.size());


    }

}
