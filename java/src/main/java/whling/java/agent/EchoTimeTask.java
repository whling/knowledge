package whling.java.agent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EchoTimeTask {
    public void echoTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        System.out.println(String.format("当前时间是：[%s]", simpleDateFormat.format(new Date())));
    }
}
