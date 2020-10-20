package com.weblog.utils;

import org.springframework.stereotype.Component;

/**
 * @author G
 * @version 1.0
 * @date 2020/10/5 20:33
 */

@Component
public class Result {
    //0失败，1成功，-1不知道
    private int code;
    private Object data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Result(int code,Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public Result() {
    }

    public static Result result(int code,Object object, String msg){
        return new Result(code,object,msg);
    }
}
