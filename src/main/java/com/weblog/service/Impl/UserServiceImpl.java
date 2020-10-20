package com.weblog.service.Impl;

import com.weblog.domain.Article;
import com.weblog.domain.User;
import com.weblog.domain.UserExample;
import com.weblog.mapper.UserMapper;
import com.weblog.service.UserServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author G
 * @version 1.0
 * @date 2020/10/6 13:08
 */
@Service
public class UserServiceImpl implements UserServie {
    @Autowired
    UserMapper userMapper;

    /**
     * @param user
     * @return -1用户名不存在，1登录成功，0密码错误
     */
    /*@Override
    public int login(User user) {
        User user1 = userMapper.selectByUsername(user.getUsername());
        if (user1 == null) {
            //用户名不存在
            return -1;
        } else {
            //md5对密码加密校验
            String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
            //用equal判断
            if (password.equals(user1.getPassword())) {
                return 1;
            }
            return 0;
        }
    }*/

    /**
     * 注册
     * @param user
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int register(User user) {
        //用户名查重
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(user.getUsername());
        List<User> users = userMapper.selectByExample(userExample);
        if (!users.isEmpty()) {
            return -1;
        }
        //BCryptPasswordEncoder编码加密
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        String password=bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(password);
        //除了爷都是user
        user.setRole("user");
        //注册时间
        Timestamp time = new Timestamp(System.currentTimeMillis());
        user.setRegtime(time);
        return userMapper.insertSelective(user);
    }



}
