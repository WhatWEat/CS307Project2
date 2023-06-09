package com.cs307.bbsdatabase.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@TableName("Replies")
public class Reply implements Serializable {
    private Integer reply_id;
    private Integer post_id;
    @Nullable
    private Integer parent_id;

    @TableField(exist = false)
    private String toReply;
    private Timestamp replying_time;
    @TableField(exist = false)
    private String username;

    @TableField(exist = false)
    private boolean ifFollowed;
    private String content;
    //    private int stars;
    private Boolean anonymous;
    @TableField(exist = false)
    private int commentNum;

    @TableField(exist = false)
    private int like;
    @TableField(exist = false)
    private List<Reply> son;

    @TableField(exist = false)
    private ArrayList<User> users;

    public Reply(String content, boolean anonymous, Integer post_id) {
        this.content = content;
        this.anonymous = anonymous;
        this.post_id = post_id;
    }

    @JsonCreator
    public Reply(@JsonProperty("reply_id") Integer reply_id,
                 @JsonProperty("content") String content,
                 @JsonProperty("anonymous") Boolean anonymous,
                 @JsonProperty("parent_id") Integer parent_id) {
        this.reply_id = reply_id;
        this.content = content;
        this.anonymous = anonymous;
        if (parent_id != null) {
            this.parent_id = parent_id;
        }
        this.replying_time = new Timestamp(System.currentTimeMillis());
    }

    public Reply(@Param("reply_id") Integer reply_id,
                 @Param("content") String content,
                 @Param("replying_time") Timestamp replying_time,
                 @Param("anonymous") Boolean anonymous,
                 @Param("parent_id") Integer parent_id,
                 @Param("post_id") Integer post_id) {
        this.reply_id = reply_id;
        this.parent_id = parent_id;
        this.replying_time = replying_time;
        this.content = content;
        this.anonymous = anonymous;
        this.post_id = post_id;
    }

    public Reply(Integer post_id, String content, boolean anonymous) {
        this.post_id = post_id;
        this.parent_id = null;
        this.content = content;
        this.anonymous = anonymous;
    }

    public void setReply_id(Integer reply_id) {
        this.reply_id = reply_id;
    }

    public void setPostID(Integer post_id) {
        this.post_id = post_id;
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
                ", anonymous=" + anonymous +
                '}';
    }

    public Timestamp getReplying_time() {
        return replying_time;
    }


    public void setContent(String content) {
        this.content = content;
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

    public Integer getPost_id() {
        return post_id;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public String getContent() {
        return content;
    }


    public boolean isAnonymous() {
        return anonymous;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setSon(List<Reply> son) {
        this.son = son;
    }

    public void setIfFollowed(boolean ifFollowed) {
        this.ifFollowed = ifFollowed;
    }

    public void setReplying_time(Timestamp replying_time) {
        this.replying_time = replying_time;
    }

    public String getUsername() {
        return username;
    }

    public boolean isIfFollowed() {
        return ifFollowed;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public List<Reply> getSon() {
        return son;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getToReply() {
        return toReply;
    }

    public void setToReply(String toReply) {
        this.toReply = toReply;
    }
}
