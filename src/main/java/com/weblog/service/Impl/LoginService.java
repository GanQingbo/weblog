package com.weblog.service.Impl;

import com.weblog.domain.User;
import com.weblog.domain.UserExample;
import com.weblog.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 登录
 * @author G
 * @version 1.0
 * @date 2020/10/9 11:04
 */
@Service
public class LoginService implements UserDetailsService {
    @Autowired
    UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userMapper.selectByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("用户不存在");
        }
        return user;
    }
}
