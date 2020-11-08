package com.weblog.controller;

import com.weblog.domain.User;
import com.weblog.service.Impl.UserServiceImpl;
import com.weblog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author G
 * @version 1.0
 * @date 2020/10/5 20:31
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    Result result;
    @Autowired
    UserServiceImpl userServiceImpl;

    /*@PostMapping("/login")
    public JsonData login(User user){
        try {
            int i = userServiceImpl.login(user);
            if(i==-1){
                result.setMsg("用户名不存在");
            }else if(i==1){
                result.setMsg("登录成功");
            }else if(i==0){
                result.setMsg("密码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg("Error");
        }
        return result;
    }*/

/*    @PostMapping("/register")
    public Result register(User user){
        try {
            int i = userServiceImpl.register(user);
            if(i<0){
                result.setCode(0);
                result.setMsg("账号名已注册");
            }
            if (i>0){
                result.setCode(1);
                result.setMsg("注册成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(0);
            result.setMsg("注册失败");
        }
        return result;
    }*/
}
