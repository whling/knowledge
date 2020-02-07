package whling.mybatis.custom.session;

import java.io.InputStream;

public class SqlSessionFactoryBuilder {

    public static SqlSessionFactory build(InputStream in) {
        return new DefaultSqlSessionFactory(in);
    }
}
