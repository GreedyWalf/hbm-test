package com.qs.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Customer implements Serializable {

    private String id;
    private String name;
    private char sex;
    private boolean married;
    private Timestamp createTime;

    //注意：这个字段没有get、set方法
    private String email;

    private Integer age;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public boolean isMarried() {
        return married;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
