package whling.shiro.dao;

import java.util.Set;

public interface UserDao {

    String getPasswordByUserName(String userName);

    Set<String> getRolesByUserName(String userName);

    Set<String> getPermissionsByUserName(String userName);
}
