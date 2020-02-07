package whling.mybatis.custom.session;


import whling.mybatis.custom.Configuration;
import whling.mybatis.custom.utils.XmlConfigBuilder;

import java.io.InputStream;

public class DefaultSqlSessionFactory implements SqlSessionFactory {


    private InputStream in;

    public DefaultSqlSessionFactory(InputStream in) {
        this.in = in;
    }

    @Override
    public SqlSession openSession() {

        Configuration configuration = XmlConfigBuilder.loadConfig(in);

        DefaultSqlSession defaultSqlSession = new DefaultSqlSession(configuration);

        return defaultSqlSession;
    }

}
