package whling.java.agent;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 参考：https://www.jianshu.com/p/d47e3aa1cb5c
 */
public class MainAgent {

    /**
     * 修改idea运行配置：
     * 1.通过idea打包的方式：
     *      VM options: -javaagent:/Users/whling/IdeaProjects/knowledge/java/out/artifacts/java_jar/java.jar
     *
     * 2.通过maven打包：
     *      VM options: -javaagent:/Users/whling/IdeaProjects/knowledge/java/target/java-1.0-SNAPSHOT.jar
     *
     * 多个agent的话，可以配置多个-javaagent（空格隔开）
     *      -javaagent:/Users/whling/IdeaProjects/knowledge/java/target/java-1.0-SNAPSHOT.jar -javaagent:/Users/whling/IdeaProjects/knowledge/java/out/artifacts/java_jar/java.jar
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("MainAgent...");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        System.out.println(String.format("当前时间是：[%s]", simpleDateFormat.format(new Date())));
    }
}
