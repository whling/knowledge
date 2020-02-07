package whling.mybatis.learn.mapper;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import whling.mybatis.learn.entity.User;

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
