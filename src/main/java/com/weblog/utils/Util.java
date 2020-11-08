package com.weblog.utils;

import com.weblog.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author G
 * @version 1.0
 * @date 2020/11/8 22:42
 */

public class Util {
    /**
     *
     * @return 当前用户
     */
    public static User getCurrentUser(){
        User user  =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }
}
