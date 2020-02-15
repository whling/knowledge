package whling.java.agent;

import com.sun.tools.attach.VirtualMachine;

public class RunAttach {

    public static void main(String[] args) throws Exception {
        // args[0]传入的是某个jvm进程的pid
        String targetPid = args[0];

        VirtualMachine vm = VirtualMachine.attach(targetPid);

        vm.loadAgent("/Users/whling/IdeaProjects/knowledge/java/target/java-1.0-SNAPSHOT.jar",
                "toagent");

    }
}
