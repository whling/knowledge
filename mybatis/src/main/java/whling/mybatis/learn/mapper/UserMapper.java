package whling.mybatis.learn.mapper;

import whling.mybatis.learn.entity.User;

import java.util.List;

public interface UserMapper {

    List<User> findAll();
}
