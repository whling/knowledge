package whling.java.agent;

import com.sun.tools.attach.VirtualMachine;

public class RunAttach {

    /**
     * jdk1.6开始支持在程序运行的过程中动态的加载代理类，不再需要在启动的指令中
     *
     * @param args
     */
    public static void main(String[] args) {
        try {

            VirtualMachine virtualMachine = VirtualMachine.attach("3713");
            System.out.println("hello,RunAttach");
            /**
             * 执行java.jar中的Agent-Class的agentmain方法
             */
            virtualMachine.loadAgent("/Users/whling/IdeaProjects/knowledge/java/out/artifacts/java_jar/java.jar");

        } catch (Exception e) {
        }


    }
}
