package whling.springmvc.inner;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import whling.springmvc.servlet.DemoServlet;

import javax.servlet.ServletException;
import java.io.File;

public class TomcatStart {


    public static final int TOMCAT_PORT = 8080;
    public static final String TOMCAT_HOSTNAME = "127.0.0.1";
    public static final String WEBAPP_PATH = "src/main";

    public static void main(String[] args) throws ServletException, LifecycleException {
        System.out.println(System.getProperty("user.dir"));
        System.out.println(TomcatStart.class.getResource("./").getPath());
        TomcatStart.run();
    }

    public static void run() throws ServletException, LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(TomcatStart.TOMCAT_PORT);
        tomcat.setHostname(TomcatStart.TOMCAT_HOSTNAME);
        tomcat.setBaseDir("."); // tomcat 信息保存在项目下

        /*
         * https://www.cnblogs.com/ChenD/p/10061008.html
         */
        StandardContext myCtx = (StandardContext) tomcat
                .addWebapp("", "/Users/whling/IdeaProjects/knowledge/springmvc" + File.separator + TomcatStart.WEBAPP_PATH);
        /*
         * true时：相关classes | jar 修改时，会重新加载资源，不过资源消耗很大
         * autoDeploy 与这个很相似，tomcat自带的热部署不是特别可靠，效率也不高。生产环境不建议开启。
         * 相关文档：
         * http://www.blogjava.net/wangxinsh55/archive/2011/05/31/351449.html
         */
        myCtx.setReloadable(false);
        // 上下文监听器
        myCtx.addLifecycleListener(new AprLifecycleListener());


        // 注册servlet
        tomcat.addServlet("", "demoServlet", new DemoServlet());
        // servlet mapping
        myCtx.addServletMappingDecoded("/demo.do", "demoServlet");


        tomcat.start();
        tomcat.getServer().await();


    }

}