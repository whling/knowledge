package whling.mybatis.custom.session;

import whling.mybatis.custom.Configuration;

import java.util.List;

public interface SqlSession {

    <T> T getMapper(Class<T> mapperClass);

    Configuration getConfiguration();

    List<Object> selectList(String key);
}
