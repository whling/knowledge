package whling.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import whling.shiro.domain.User;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    /**
     * 这个地方请求路径/login，会拦截login.html这个路径，所以这里叫subLogin
     * @param user
     * @param request
     * @return
     */
    @RequestMapping(value = "/subLogin")
    @ResponseBody
    public String login(User user, HttpServletRequest request) {

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        try {
            subject.login(usernamePasswordToken);
        } catch (AuthenticationException e) {
            return e.getMessage();
        }
        return "login success";
    }
}
