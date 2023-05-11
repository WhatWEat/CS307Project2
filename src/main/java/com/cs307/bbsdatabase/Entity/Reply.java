package com.cs307.bbsdatabase.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.ArrayList;

@TableName("Replies")
public class Reply {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer postID;
    private Integer parentReplyID;
    private Integer userID;
    private String content;
    private int stars;
    private boolean anonymous;

    @TableField(exist = false)
    private ArrayList<User> users;

    public Reply(Integer id, Integer postID, Integer parentReplyID, Integer userID, String content,
        int stars, boolean anonymous) {
        this.id = id;
        this.postID = postID;
        this.parentReplyID = parentReplyID;
        this.userID = userID;
        this.content = content;
        this.stars = stars;
        this.anonymous = anonymous;
    }

    public Reply(Integer postID, Integer userID, String content, int stars, boolean anonymous) {
        this.postID = postID;
        this.userID = userID;
        this.parentReplyID = null;
        this.content = content;
        this.stars = stars;
        this.anonymous = anonymous;
    }

    public Reply(Integer postID, Integer parentReplyID, Integer userID, String content, int stars,
        boolean anonymous) {
        this.postID = postID;
        this.parentReplyID = parentReplyID;
        this.userID = userID;
        this.content = content;
        this.stars = stars;
        this.anonymous = anonymous;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPostID(Integer postID) {
        this.postID = postID;
    }

    public void setParentReplyID(Integer parentReplyID) {
        this.parentReplyID = parentReplyID;
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

    public Integer getId() {
        return id;
    }

    public Integer getPostID() {
        return postID;
    }

    public Integer getParentReplyID() {
        return parentReplyID;
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
