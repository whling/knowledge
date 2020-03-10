package whling.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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


        if (subject.hasRole("administrator")) {
            return "welcome administrator";
        }

        return "login success";
    }


//    @RequiresRoles(value = {"administrator"})
    @RequestMapping(value = "/testRole", method = RequestMethod.GET)
    @ResponseBody
    public String testRole(){
        return "testRole success";
    }

//    @RequiresRoles(value = {"user1"})
    @RequestMapping(value = "/testRole1", method = RequestMethod.GET)
    @ResponseBody
    public String testRole1(){
        return "testRole1 success";
    }

//    @RequiresPermissions(value = {"user:insert"})
    @RequestMapping(value = "/testPermission", method = RequestMethod.GET)
    @ResponseBody
    public String testPermission(){
        return "testPermission success";
    }

//    @RequiresPermissions(value = {"user:query"})
    @RequestMapping(value = "/testPermission1", method = RequestMethod.GET)
    @ResponseBody
    public String testPermission1(){
        return "testPermission1 success";
    }

}
