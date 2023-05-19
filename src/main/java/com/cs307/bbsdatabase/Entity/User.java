package com.cs307.bbsdatabase.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

@TableName("Users")
public class User implements Serializable {


    /*
    如果数据库表中属性名与类中属性名不对应，可以使用注解
    @TableField("xxx")
    来进行指向，xxx为数据库中属性名
    * */
    private final String username;
    @TableField("registration_time")
    private final Timestamp registration;

    @TableField("phone_number")
    private String phone;

    @TableField("user_id")
    private String id;
    private String password;





    //exist注解表示该字段在仅在类中存在，在数据库中不存在
    @TableField(exist = false)
    private ArrayList<User> follow = new ArrayList<>();

    @TableField(exist = false)
    private ArrayList<User> block = new ArrayList<>();


    public User(
                String username,
                Timestamp registration,
                String phone,
                String id,
                String password
                ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.registration = registration;
    }


    @JsonCreator
    public User(@JsonProperty("username") String username,
                @JsonProperty("phone") String phone,
                @JsonProperty("password") String password) {
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

    public void setId(String id) {
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

    public String getId() {
        return id;
    }
}
