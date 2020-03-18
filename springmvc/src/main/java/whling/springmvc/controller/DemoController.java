package whling.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

@Controller
public class DemoController {
    
    static int count = 0;

    @RequestMapping(value = "/demo")
    @ResponseBody
    public String demo(String name) {

        count++;
        System.out.println("demo controller .." + count);

        if (Objects.isNull(name)) {
            return "hello world";
        }

        return name;
    }
}
