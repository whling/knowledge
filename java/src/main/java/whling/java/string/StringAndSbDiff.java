package whling.java.string;

import java.util.LinkedList;
import java.util.List;

/**
 * String和StringBuilder效率不同的原理:
 * mutable（可变数据类型）和immutable（不可变数据类型）
 * <p>
 * String是immutable的变量，一旦被创建，它的值就不能被改变。
 * 注意：改变一个变量是将该变量指向另一个值的存储空间，二改变一个变量的值是将该变量当前指向的值的存储空间写入一个新的值。
 * <p>
 * 当string "+"的时候,是新建了一个String空间然后将原来变量的指向这个空间。原来的String被废弃只能等待着被垃圾回收。
 * 又通过后来学习的垃圾回收的机制我们知道这是非常浪费资源的，当然也有其安全性。
 * <p>
 * 参考：https://www.cnblogs.com/blairwaldorf/p/9191339.html
 */
public class StringAndSbDiff {

    public static void main(String[] args) {

        stringAdress();

        stringInCollection();

        stringInCollection2();

    }

    private static void stringAdress() {
        /**
         * 内存中会同时存在"ab"和"abc"
         */
        String s = "ab";
        String t = s;
        // s1+="q" 或者 s1=s1+"q" 或者 s1=s1.concat("q")都是在jvm中重新开辟了一个空间存储新的字符串，而使用stringbuilder这种的做法是直接替换
        s = s + "c";
        // ab
        System.out.println(t);
    }

    private static void stringInCollection() {
        List<String> list = new LinkedList<>();
        String s1 = new String("aaa");
        list.add(s1);
        System.out.println(list.contains(s1));//true
        s1 = s1.concat("q");
        System.out.println(list.contains(s1));//false<br>
    }

    private static void stringInCollection2() {
        List<String> list = new LinkedList<>();
        String s1 = new String("aaa");
        list.add(s1);
        System.out.println(list.contains(s1));//true
        s1 =list.get(0).concat("q");
        list.add(s1);
        System.out.println(list.contains(s1));//false<br>
    }
}
