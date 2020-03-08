package whling.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import whling.shiro.dao.UserDao;

import javax.annotation.Resource;
import java.util.*;

public class CustomRealm extends AuthorizingRealm {

    @Resource
    private UserDao userDao;

//    Map<String, String> userMap = new HashMap(2);

    public static final String REAL_NAME = "custom-realm";

    {
//        userMap.put("whling", "202cb962ac59075b964b07152d234b70");  // 未加盐
//        userMap.put("whling", "186c85cc244d41d14894925e6e482deb");  // 加盐
        super.setName(REAL_NAME);
    }

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String userName = (String) principalCollection.getPrimaryPrincipal();
        Set<String> roles = getRolesByUserName(userName);

        Set<String> permissions = getPermissionsByUserName(userName);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(roles);
        simpleAuthorizationInfo.addStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    private Set<String> getPermissionsByUserName(String userName) {
        return userDao.getPermissionsByUserName(userName);
//        HashSet<String> permissions = new HashSet<>();
//        permissions.add("user:insert");
//        permissions.add("user:delete");
//        return permissions;
    }

    private Set<String> getRolesByUserName(String userName) {
        return userDao.getRolesByUserName(userName);
//        HashSet<String> roles = new HashSet<>();
//        roles.add("administrator");
//        roles.add("user");
//        return roles;
    }

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName = (String) authenticationToken.getPrincipal();
        String password = getPasswordByUserName(userName);

        if (password == null) {
            return null;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userName, password, REAL_NAME);
        // 设置加盐信息
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("whling"));
        return simpleAuthenticationInfo;
    }

    private String getPasswordByUserName(String userName) {

        return userDao.getPasswordByUserName(userName);
//        return userMap.get(userName);
    }

    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("123");
        System.out.println(md5Hash.toString());

        Md5Hash md5 = new Md5Hash("123","whling");
        System.out.println(md5.toString());
    }

}
