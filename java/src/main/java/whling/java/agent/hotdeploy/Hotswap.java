package whling.java.agent.hotdeploy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;

/**
 * 1、 首要了解类加载器是什么
 *
 * 我们的代码由.java 编译成 .class，要想在jvm中运行，需要被加载到JVM中。
 *
 * 而加载class的工作是有类加载器实例去实现的，类加载器支持通过文件目录，jar，zip，网络等多种途径，加载class类文件。
 *
 * JVM启动后就默认有三个类加载器实例，负责去加载不同位置的class。
 *
 * > 核心类库加载器 BootStrap ClassLoader，负责加载jdk安装目录下lib文件夹里面的jar包，我们的String.class，System.class这些类都放在这个目录下面，启动jvm就会去加载，必不可少。
 *
 * > 拓展类库加载器 Extension ClassLoader，负责加载jdk安装目录下lib/ext文件夹里面的jar包，这里面是一些jdk的拓展jar包，比如zipfs.jar这样的包或工具类。拓展的意思就是在某些情况下，这些jar包不加载也不影响jvm工作。
 *
 * > 应用程序代码加载器 Application ClassLoader，负责加载我们自己写的程序代码，通过java命令 -cp 或者 -classpath告诉jvm我们的代码class存放位置。如果我们的程序是jar包运行，你可以在jar包 META-INF目录MANIFEST.MF文件里面看到一个Class-Path: .配置，这就是指定代码位置的。
 *
 * 2、 热加载
 *
 * java中有这么一个概念“同一个类名，同一个类加载器实例加载的，代表是同一个类”。
 *
 * 如果我们要自己实现业务代码的热加载，就不能用默认的类加载器实例，因为同一个类加载器实例加载一次后会存起来，后面的class文件就算更新了也不会再次加载了（看源码实现）
 * 所以自定义类加载器，打破双亲委派模型，每次主动加载。
 *
 */
public class Hotswap {

    public static void main(String[] args) throws Exception {
        loadHelloWorld();
        loadHelloWorld();
        // 回收资源,释放HelloWorld.class文件，使之可以被替换（怕被程序占用删除不了，改为重新写入内容方式）
//        System.gc();
//        Thread.sleep(1000);// 等待资源被回收

        File fileV2 = new File("/Users/whling/IdeaProjects/knowledge/java/out/artifacts/HelloWorld.class");
        File fileV1 = new File("/Users/whling/IdeaProjects/knowledge/java/target/classes/whling/java/agent/hotdeploy/HelloWorld.class");

        FileInputStream inputStream = new FileInputStream(fileV2);
        FileOutputStream outputStream = new FileOutputStream(fileV1);

//        fileV1.delete(); //删除V1版本
//        fileV2.renameTo(fileV1); //更新V2版本

//        byte[] b = new byte[1024];
//        int i = 0;
//        while ((i = inputStream.read(b))!=-1) {
//            outputStream.write(b, 0, i);
//        }


        System.out.println("Update success!");
        loadHelloWorld();
    }

    public static void loadHelloWorld() throws Exception {
//        ClassLoader classLoader = this.getClass().getClassLoader();
//        classLoader.d
        MyClassLoader myLoader = new MyClassLoader(); //自定义类加载器
//        Class<?> class1 = myLoader.loadClass("whling.java.agent.hotdeploy.HelloWorld");
        /**
         * 这里调用自定义类加载器的findClass方法，而不是loadClass方法，是因为没有重写loadClass方法，还是会满足jdk的双亲委派模型，
         * 那么在第二遍加载的时候，就已经存在了，就不会再加载。
         *
         * 所以当loadClass中参数的地址不是classpath下，意味着不能使用系统类加载器加载时，就会走自定义类加载器加载。
         */
        Class<?> class1 = myLoader
                .findClass("whling.java.agent.hotdeploy.HelloWorld");//类实例
        Object obj1 = class1.newInstance(); //生成新的对象
        Method method = class1.getMethod("say");
        method.invoke(obj1); //执行方法say
        System.out.println(obj1.getClass()); //对象
        System.out.println(obj1.getClass().getClassLoader()); //对象的类加载器
    }
}