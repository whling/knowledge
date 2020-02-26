package whling.jdbc;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

public class JdbcTest {


    public static void main(String[] args) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            System.out.println(drivers.nextElement());
        }


        String str = "ctcc-gst-gansu-hy，\tctcc-gst-guangxi-hy，ctcc-gst-hebei-hy，\tctcc-gst-jiangxi-hy，ctcc-gst-neimenggu-hy，ctcc-gst-shandong-yx，ctcc-gst-shanxi2-hy，\tctcc-gst-sichuang-hy，\tcucc-gst-henan-yx，cucc-gst-henan-hy，\tcucc-gst-hubei-yx,cucc-gst-jiangsu-hy，cucc-gst-liaoning-hy，cucc-gst-liaoning-yx，\tcucc-gst-neimenggu-hy，cucc-gst-sichuang-hy，cucc-gst-sichuang-yx";


        str = str.replaceAll("，", ",");

        String[] split = str.split(",");

        List<String> list = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        String ss = "";
        for (int i = 0; i < split.length; i++) {

            String s = split[i];
            String trim = s.trim();
            String res = trim.replace("\t", "");
            list.add(res);
        }


        String result = list.stream().map(s -> "'" + s + "'").collect(Collectors.joining(","));
//        for (String s : collect) {
//            System.out.println(s);
//        }
        System.out.println(result);

    }
}
