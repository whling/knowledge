package whling.java.agent.hotdeploy;


/**
 * https://blog.csdn.net/u011490072/article/details/81560295
 */
public class ClassLoaderTest {

    public static void main(String[] args) throws Exception {
        ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();

        System.out.println(classLoader);

        Class<?> clazz = classLoader.loadClass("java.lang.String");

        Object o = clazz.newInstance();
        String str = (String) clazz.cast(o);

        System.out.println(str);


        MyClassLoader myClassLoader = new MyClassLoader();
        System.out.println(myClassLoader);

        Class<?> aClass = myClassLoader.loadClass("java.lang.String");
        Class<?> bClass = myClassLoader.loadClass("whling.java.agent.hotdeploy.HelloWorld1");//被系统类加载器加载
        Class<?> cClass = myClassLoader.loadClass("whling.java.agent.hotdeploy.HelloWorld");// 被自定义类加载器加载

//        Class<?> aClass = myClassLoader.findClass("whling.java.agent.hotdeploy.HelloWorld");
        System.out.println(aClass);
    }
}
