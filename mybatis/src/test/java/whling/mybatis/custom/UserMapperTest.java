package whling.mybatis.custom;


import org.junit.Test;
import whling.mybatis.custom.io.Resources;
import whling.mybatis.custom.session.SqlSession;
import whling.mybatis.custom.session.SqlSessionFactory;
import whling.mybatis.custom.session.SqlSessionFactoryBuilder;
import whling.mybatis.learn.entity.User;
import whling.mybatis.learn.mapper.UserMapper;

import java.io.InputStream;
import java.util.List;

public class UserMapperTest {


    @Test
    public void testFindAll() {

        try (InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml")) {
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<User> userList = userMapper.findAll();
            for (User user : userList) {
                System.out.println(user);
            }
        } catch (Exception e) {

        }
    }
}
