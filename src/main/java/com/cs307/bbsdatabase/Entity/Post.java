package com.cs307.bbsdatabase.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.sql.Timestamp;
import java.util.ArrayList;
/*
帖子的id、作者、发布时间、发布城市为final
允许改动content、title、categories
 */
@TableName("posts")
public class Post {
    @TableId(type = IdType.AUTO)
    private Integer postid;

    private String title;
    private String content;
    private final Timestamp posting_time;
    @TableField(exist = false)
    private Integer User_id;
    @TableField(exist = false)
    private ArrayList<String> categories;
    @TableField(exist = false)
    private  City postCity;

    public Post() {
        this.posting_time = new Timestamp(System.currentTimeMillis());
    }

    public Post( String title, String content) {
        this.title = title;
        this.content = content;
        this.posting_time = new Timestamp(System.currentTimeMillis());
    }
    public Post( String title, String content, ArrayList<String> categories) {
        this.title = title;
        this.content = content;
        this.categories = categories;
        this.posting_time = new Timestamp(System.currentTimeMillis());
    }

    public Integer getPost_id() {
        return postid;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getPosting_time() {
        return posting_time;
    }

    public Integer getUser_id() {
        return User_id;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public City getPostCity() {
        return postCity;
    }

    public void setPost_id(Integer postid) {
        this.postid = postid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }
}
