package whling.java.agent.hotdeploy;


/**
 * https://blog.csdn.net/u011490072/article/details/81560295
 */
public class ClassLoaderTest {
    /**
     * 假如我们⾃己写了⼀个java.lang.String的类，我们是否可以替换调JDK本⾝身的类?
     * 答案是否定的。我们不能实现。为什么呢?我看很多⺴⽹网上解释是说双亲委托机制解决这个问
     * 题，其实不是⾮非常的准确。因为双亲委托机制是可以打破的，你完全可以⾃⼰写一个
     * classLoader来加载⾃己写的java.lang.String类，但是你会发现也不会加载成功，具体就是
     * 因为针对java.*开头的类，jvm的实现中已经保证了必须由bootstrp来加载。
     *
     * @param args
     * @throws Exception
     */

    public static void main(String[] args) throws Exception {
        ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();

        System.out.println(classLoader);

        Class<?> clazz = classLoader.loadClass("java.lang.String");

        Object o = clazz.newInstance();
        String str = (String) clazz.cast(o);

        System.out.println(str);


        MyClassLoader myClassLoader = new MyClassLoader();
        System.out.println(myClassLoader);

        /**
         * 如果双亲是 null，则使用启动类加载器加载，如果事变，则使用当前类加载器加载
         * 双亲为 null 一般有2种情况，1. 双亲是启动类加载器（例如扩展类加载器）。2. 自己就是启动类加载器。
         */
        Class<?> aClass = myClassLoader.loadClass("java.lang.String");
        Class<?> cClass = myClassLoader.loadClass("whling.java.agent.hotdeploy.HelloWorld");// 被系统类加载器加载
//        Class<?> bClass = myClassLoader.loadClass("whling.java.agent.hotdeploy.HelloWorld1");//被自定义类加载器加载,发现加载不到，抛出异常

//        Class<?> aClass = myClassLoader.findClass("whling.java.agent.hotdeploy.HelloWorld");
        System.out.println(aClass);
    }
}
