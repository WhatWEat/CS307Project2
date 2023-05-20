package com.cs307.bbsdatabase.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;

@TableName("Replies")
public class Reply implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer reply_id;
    @TableField(exist = false)
    private Integer postID;
    private Integer parent_id;
    @TableField(exist = false)
    private Integer userID;
    private String content;
    private int stars;
    private boolean anonymous;

    @TableField(exist = false)
    private ArrayList<User> users;

    public Reply(String content, boolean anonymous) {
        this.stars = 0;
        this.content = content;
        this.anonymous = anonymous;
    }

    @JsonCreator
    public Reply(@JsonProperty("reply_id") Integer reply_id,
                 @JsonProperty("content")String content,
                 @JsonProperty("stars")int stars,
                 @JsonProperty("anonymous")boolean anonymous,
                 @JsonProperty("parent_id")Integer parent_id) {
        this.reply_id = reply_id;
        this.content = content;
        this.stars = stars;
        this.anonymous = anonymous;
        if (parent_id != null) {
            this.parent_id = parent_id;
        }
    }

    public Reply(Integer postID, String content, int stars, boolean anonymous) {
        this.postID = postID;
        this.parent_id = null;
        this.content = content;
        this.stars = stars;
        this.anonymous = anonymous;
    }

    public void setReply_id(Integer reply_id) {
        this.reply_id = reply_id;
    }

    public void setPostID(Integer postID) {
        this.postID = postID;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    @Override
    public String toString() {
        return "Reply{" +
                "reply_id:" + reply_id +
                ", parent_id:" + parent_id +
                ", content:'" + content + '\'' +
                ", stars:" + stars +
                ", anonymous=" + anonymous +
                '}';
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public Integer getReply_id() {
        return reply_id;
    }

    public Integer getPostID() {
        return postID;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public Integer getUserID() {
        return userID;
    }

    public String getContent() {
        return content;
    }

    public int getStars() {
        return stars;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
