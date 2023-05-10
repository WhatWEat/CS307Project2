package com.cs307.bbsdatabase.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.beans.Transient;
import java.sql.Timestamp;
import java.util.ArrayList;

@TableName("Users")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    /*
    如果数据库表中属性名与类中属性名不对应，可以使用注解
    @TableField("xxx")
    来进行指向，xxx为数据库中属性名
    * */
    private final String username;
    private String password;
    private String phone;
    private final Timestamp registration;

    //exist注解表示该字段在仅在类中存在，在数据库中不存在
    @TableField(exist = false)
    private ArrayList<User> follow = new ArrayList<>();
    public User(String phone, String username, String password) {
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.registration = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", phone='" + phone + '\'' +
            ", registration=" + registration +
            '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public Timestamp getRegistration() {
        return registration;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }
    public Integer getId() {
        return id;
    }
}
