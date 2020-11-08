package com.weblog.domain;

/**
 * @author G
 * @version 1.0
 * @date 2020/11/7 23:40
 */
public class Role {
    /**
     * id
     */
    private Integer id;
    /**
     * 权限角色
     */
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
