package whling.mybatis.custom;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Configuration {
    private static final Logger log = LoggerFactory.getLogger(Configuration.class);

    private String driverName;
    private String username;
    private String password;
    private String url;

    private ComboPooledDataSource dataSource = new ComboPooledDataSource();

    private Map<String, Mapper> mappers = new HashMap<>();

    public Configuration(String driverName, String username, String password, String url) {
        this.driverName = driverName;
        this.username = username;
        this.password = password;
        this.url = url;
        initDataSource();
    }

    private void initDataSource() {
        try {
            dataSource.setDriverClass(driverName);
            dataSource.setUser(username);
            dataSource.setPassword(password);
            dataSource.setJdbcUrl(url);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void addMapper(Map<String, Mapper> mapperMap) {
        mappers.putAll(mapperMap);
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("数据库连接失败." + e);
        }
    }

    public static void close(ResultSet rs, Statement state, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
            }
        }
        if (state != null) {
            try {
                state.close();
            } catch (SQLException e) {
            }
        }
        if (conn != null) {
            try {
                conn.close();// 归还
            } catch (SQLException e) {
            }
        }
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, Mapper> getMappers() {
        return mappers;
    }
}
