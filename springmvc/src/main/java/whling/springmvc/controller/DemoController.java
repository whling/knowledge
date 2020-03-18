package whling.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

@Controller
public class DemoController {
    
    static Set<Thread> ts = new HashSet();
    
    static ThreadLocal<Integer> tl = ThreadLocal.withInitial(()->{
        ts.add(Thread.currentThread());
        return 0;
    });
    
    static int count = 0;

    @RequestMapping(value = "/add")
    @ResponseBody
    public String add(String name) {

        count++;
        sout
        tl.set(tl.get() + 1);
        System.out.println("demo controller .." + count);

        if (Objects.isNull(name)) {
            return "hello world " + count;
        }

        return name;
    }

    @RequestMapping(value = "/stat")
    @ResponseBody
    public String stat() {
        return String.valueOf(count);
    }


}
