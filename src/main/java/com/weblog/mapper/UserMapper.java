package com.weblog.mapper;

import java.util.List;

import com.weblog.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    List<User> selectAllUsers();
    User selectByUsername(@Param("username") String username);
}