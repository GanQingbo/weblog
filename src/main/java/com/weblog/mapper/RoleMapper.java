package com.weblog.mapper;

import com.weblog.domain.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author G
 * @version 1.0
 * @date 2020/11/8 0:01
 */
@Mapper
@Repository
public interface RoleMapper {
    List<Role> getRolesByUserId(Integer uid);

}
