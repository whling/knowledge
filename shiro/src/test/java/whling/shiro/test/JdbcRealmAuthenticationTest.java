package whling.shiro.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class JdbcRealmAuthenticationTest {


    private DruidDataSource dataSource;

    {
        dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/shiro");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

    }

    /**
     * 使用默认的jdbcRealm认证等sql语句
     */
    @Test
    public void testAuthentication() {

        JdbcRealm realm = new JdbcRealm();
        realm.setPermissionsLookupEnabled(true); // 如果要校验用户的权限信息，则需要把此开关打开，默认false
        realm.setDataSource(dataSource);

        /**
         * 可以使用自己的sql查询认证/角色/权限信息
         */
//        realm.setAuthenticationQuery("");
//        realm.setUserRolesQuery("");
//        realm.setPermissionsQuery("");

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

        subject.checkRoles("administrator","user");

        subject.checkPermissions("user:query");
    }
}
