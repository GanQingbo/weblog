package com.weblog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author G
 * @version 1.0
 * @date 2020/10/8 15:42
 */
@RestController
@RequestMapping("/")
public class HelloController {
    @RequestMapping("hello")
    public String hello(){
        return "hello World";
    }
    @RequestMapping("/user/hello")
    public String user(){
        return "user";
    }
    @RequestMapping("/admin/hello")
    public String admin(){
        return "admin";
    }
}
