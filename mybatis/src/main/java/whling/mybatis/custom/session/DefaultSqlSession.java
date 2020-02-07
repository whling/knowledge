package whling.mybatis.custom.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import whling.mybatis.custom.Configuration;
import whling.mybatis.custom.Mapper;
import whling.mybatis.custom.utils.Converter;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public class DefaultSqlSession implements SqlSession {

    private static final Logger log = LoggerFactory.getLogger(DefaultSqlSession.class);

    private Configuration cfg;

    public DefaultSqlSession(Configuration cfg) {
        this.cfg = cfg;
    }

    @Override
    public <T> T getMapper(Class<T> mapperClass) {

        return (T) Proxy.newProxyInstance(mapperClass.getClassLoader(), new Class[]{mapperClass}, new MapperInvocationHandler(this));
    }

    @Override
    public Configuration getConfiguration() {
        return cfg;
    }

    @Override
    public List<Object> selectList(String key) {
        Connection connection = cfg.getConnection();
        Map<String, Mapper> mappers = cfg.getMappers();

        Mapper mapper = mappers.get(key);

        String sql = mapper.getSql();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            return Converter.converList(resultSet, mapper.getReturnType());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }
}
