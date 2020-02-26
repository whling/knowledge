package whling.java.string;

/**
 * string.intern()方法的作用是：将首次出现的字符串放入字符串常量池中。
 *
 * 这段代码在JDK 6中运行，会得到两个false，而在JDK 7中运行，会得到一个true和一个false。
 *
 * 产生差异的原因是：在JDK 6中，intern()方法会把首次遇到的字符串实例复制到永久代的字符串常量池中存储，返回的也是永久代里面这个字符串实例的引用，而由StringBuilder创建的字符串实例在Java堆上，所以必然不是同一个引用，将返回false。
 *
 * 而JDK 1.7（以及部分其他虚拟机，例如JRocki）的intern()实现就不需要再拷贝字符串的实例到永久代了，既然字符串常量池已经移到了Java堆中，那只需要在常量池里记录一下首次出现的实例引用即可，因此intern()返回的引用和由StringBuilder创建的那个字符串实例就是同一个。
 *
 * 对str2比较返回false是**因为“java”这个字符串在执行StringBuilder.toString()之前已经出现过，**字符串常量池中已经有它的引用了，不符合intern()方法要求“首次出现”的原则，而“计算机软件”这个字符串则是首次出现的，因此返回true。
 *
 * sum.misc.Version里面的launcher_name字段的值就是“java”
 *
 * https://www.toutiao.com/i6793996527397241351/
 *
 */
public class RuntimeConstantPoolOOM {

    public static void main(String[] args) {

        String str1 = new StringBuffer("计算机").append("科学").toString();

        System.out.println(str1.intern() == str1);


        String str2 = new StringBuffer("ja").append("va").toString();

        System.out.println(str2.intern() == str2);

    }
}
