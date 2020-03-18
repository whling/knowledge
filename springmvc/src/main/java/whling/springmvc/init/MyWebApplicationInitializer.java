package whling.springmvc.init;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import whling.springmvc.config.AppConfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * 在servlet3.0之后有一个规范就是servlet容器在启动之后会通过SPI机制，
 * 解析配置在classpath路径下META-INF/services/javax.servlet.ServletContainerInitializer配置的类
 * 里面的类需要实现ServletContainerInitializer接口。
 *
 * 该类可以配置注解@HandlesTypes() 来关注自己想要关注的类
 * 参考 {@link org.springframework.web.SpringServletContainerInitializer}
 */
public class MyWebApplicationInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext ac = new AnnotationConfigWebApplicationContext();
        ac.register(AppConfig.class);
        ac.refresh();

        DispatcherServlet servlet = new DispatcherServlet(ac);
        ServletRegistration.Dynamic registration = servletContext.addServlet("app", servlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/");
    }
}
