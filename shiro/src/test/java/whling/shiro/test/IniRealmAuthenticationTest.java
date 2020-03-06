package whling.shiro.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class IniRealmAuthenticationTest {


    @Test
    public void testAuthentication() {

        IniRealm realm = new IniRealm("classpath:user.ini");

        // 构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(realm);

        // 主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        AuthenticationToken token = new UsernamePasswordToken("whling","123");
        subject.login(token);

        System.out.println("authenticated:" + subject.isAuthenticated());

//        subject.logout();
//        System.out.println("authenticated:" + subject.isAuthenticated());

        subject.checkRoles("administrator");

        subject.checkPermissions("user:insert");
    }
}
