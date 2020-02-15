package whling.java.agent;

import com.google.common.io.Closer;
import whling.java.agent.transformer.EchoTimeTaskTransformer;
import whling.java.agent.transformer.MyClassFileTransformer;

import java.io.*;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;

/**
 * java探针技术，参考：https://www.cnblogs.com/aspirant/p/8796974.html
 * <p>
 * <p>
 * 写完这个类后，我们还需要做一步配置工作。
 * <p>
 * 在 src 目录(maven工程在resources)下添加 META-INF/MANIFEST.MF 文件，内容按如下定义：
 * <p>
 * Manifest-Version: 1.0
 * Premain-Class: whling.java.agent.Agent
 * Can-Redefine-Classes: true
 * <p>
 * 手动打包：https://blog.csdn.net/danfeixia/article/details/82542402
 */
public class Agent {

    /**
     * Premain-Class类需要有premain方法
     */

    /**
     * 该方法在main方法之前运行，与main方法运行在同一个JVM中
     * 并被同一个System ClassLoader装载
     * 被统一的安全策略(security policy)和上下文(context)管理
     *
     * @param agentArgs
     * @param inst
     */
    public static void premain(String agentArgs, Instrumentation inst) {

        System.out.println("====premain1 方法执行");
        System.out.println("agentArgs1 is:" + agentArgs);
        MyClassFileTransformer myClassFileTransformer = new MyClassFileTransformer();
        inst.addTransformer(myClassFileTransformer);
    }

    /**
     * 如果不存在 premain(String agentOps, Instrumentation inst)
     * 则会执行 premain(String agentOps)
     *
     * @param agentArgs
     */
    public static void premain(String agentArgs) {

        System.out.println("====premain方法执行2====");
        System.out.println("agentArgs2 is:" + agentArgs);
    }

    /**
     * java清单列表需要配置：Agent-Class
     *
     * @param agentArgs
     * @param inst
     */
    public static void agentmain(String agentArgs, Instrumentation inst) throws ClassNotFoundException {
        System.out.println("====agentmain方法执行1====");
        inst.addTransformer(new EchoTimeTaskTransformer());

        Closer closer = Closer.create();

        FileInputStream fileInputStream = null;
        ByteArrayOutputStream bos = null;
        try {
            File file = new File("/Users/whling/IdeaProjects/knowledge/dubbo/target/classes/whling/java/agent/EchoTimeTask.class");
            fileInputStream = closer.register(new FileInputStream(file));
            bos = closer.register(new ByteArrayOutputStream((int) file.length()));
            byte[] buffer = new byte[1024];
            int byteRead;
            while ((byteRead = fileInputStream.read(buffer, 0, 1024)) != -1) {
                bos.write(buffer, 0, byteRead);
            }
            /**
             * 将java模块下的EchoTimeTask内容替换dubbo模块下的EchoTimeTask
             */
            inst.redefineClasses(new ClassDefinition(Class.forName("whling.java.agent.EchoTimeTask"), bos.toByteArray()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                closer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 没有实现上述方法[agentmain(String agentArgs, Instrumentation inst)]，JVM则调用下面的方法。
     *
     * @param agentArgs
     */
    public static void agentmain(String agentArgs) {
        System.out.println("====agentmain方法执行2====");
        System.out.println("agentArgs2 is:" + agentArgs);
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
    }
}
