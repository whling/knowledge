package whling.springmvc.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 对同一资源进行并发修改，会有线程安全问题
 * 解决思路主要有：
 * 1。排队（加锁，同一时刻只保持某一个线程能够访问）
 * 2。投票（paxos/raft协议之类，同一时刻也只能有一个主导者）
 * 3。避免（GIT分布式版本控制，做多版本，冲突解决算法/ThreadLocal做多线程隔离，对结果的获取其实就是一种很简单的结果合并，冲突解决）
 *      写时复制技术copy-on-write也有点这个意思
 */
@Controller
public class DemoController {

    static Set<Val> ts = new HashSet();

    /**
     * 俩种方式：1.class override 2.functional
     */
    static ThreadLocal<Val> tl = ThreadLocal.withInitial(() -> {
        Val val = new Val();
        val.setCount(0);
        ts.add(val);
        return val;
    });

    static int count = 0;

    @RequestMapping(value = "/add")
    @ResponseBody
    public void add(String name, HttpServletRequest request,
                    HttpServletResponse response) throws Exception {

        count++;
        Val val = tl.get();
        val.setCount(val.getCount() + 1);
        if (StringUtils.isNotEmpty(String.valueOf(count))) {
            System.out.println("demo controller .." + count);
        }

        if (Objects.isNull(name)) {
            request.setAttribute("attr", "hello world " + count);
        }

//        request.getRequestDispatcher("/dispatch").forward(request, response);
        response.sendRedirect("/dispatch?attr=hello");
    }

    @RequestMapping(value = "/dispatch")
    @ResponseBody
    public String dispatch(HttpServletRequest request) {
        return request.getAttribute("attr") != null ? (String) request.getAttribute("attr") : "hello world";
    }

    @RequestMapping(value = "/stat")
    @ResponseBody
    public String stat() {
        String unsafeCount = String.valueOf(count);
        Integer safeCount = ts.stream().map(Val::getCount).reduce((a, b) -> a + b).get();
        return "unsafeCount:" + unsafeCount + " safeCount:" + safeCount;
    }

    static class Val {
        Integer count;

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }

    public static void main(String[] args) {

        AtomicInteger atomic = new AtomicInteger();

        Integer threadLocalHash = atomic.addAndGet(0x61c88647);

        System.out.println(threadLocalHash);
        System.out.println(threadLocalHash);
        System.out.println(threadLocalHash);

    }
}
