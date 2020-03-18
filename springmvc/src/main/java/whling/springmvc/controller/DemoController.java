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

    @RequestMapping(value = "/demo")
    @ResponseBody
    public String demo(String name) {

        count++;
        tl.set(tl.get() + 1);
        System.out.println("demo controller .." + count);

        if (Objects.isNull(name)) {
            return "hello world";
        }

        return name;
    }
}
