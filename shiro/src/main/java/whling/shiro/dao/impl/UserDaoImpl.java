package whling.shiro.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import whling.shiro.dao.UserDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public String getPasswordByUserName(String userName) {
        String sql = "select password from users where username = ?";
        List<String> passwordList = jdbcTemplate.query(sql, new String[]{userName}, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("password");
            }
        });
        if (CollectionUtils.isEmpty(passwordList)) {
            return null;
        }
        return passwordList.get(0);
    }

    @Override
    public Set<String> getRolesByUserName(String userName) {
        String sql = "select role_name from user_roles where username = ?";
        List<String> roles = jdbcTemplate.query(sql, new String[]{userName}, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("role_name");
            }
        });
        return new HashSet<>(roles);
    }

    @Override
    public Set<String> getPermissionsByUserName(String userName) {

        Set<String> roles = getRolesByUserName(userName);

        NamedParameterJdbcTemplate npjt = new NamedParameterJdbcTemplate(jdbcTemplate);
        HashMap<String, Object> params = new HashMap<>();
        params.put("roles", roles);
        String sql = "select permission from roles_permissions where role_name in (:roles)";

        List<String> permissions = npjt.query(sql, params, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("permission");
            }
        });

        return new HashSet<>(permissions);
    }
}
