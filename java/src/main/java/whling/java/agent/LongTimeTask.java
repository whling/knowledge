package whling.java.agent;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Timer;
import java.util.TimerTask;

public class LongTimeTask {

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new EchoTimeTask().echoTime();
                System.out.println(getPid());
            }
        }, 0, 2000L);
    }

    /**
     * 获取进程号
     *
     * @return
     */
    public static Integer getPid() {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        String name = runtime.getName();
//        System.out.println("当前进程的标识为：" + name);
        int index = name.indexOf("@");
        if (index != -1) {
            int pid = Integer.parseInt(name.substring(0, index));
//            System.out.println("当前进程的PID为：" + pid);
            return pid;
        }
        return null;
    }
}
