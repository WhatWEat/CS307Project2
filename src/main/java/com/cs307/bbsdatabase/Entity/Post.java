package com.cs307.bbsdatabase.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Objects;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.ArrayList;

/*
帖子的热度规则（好像是无关紧要之物：
被点赞+1热度
被评论+3热度
获得楼中楼+1热度
被收藏+2热度
被分享+2热度
 */

@TableName("posts")
public class Post {
    private Integer post_id;

    private String file;
    private String title;
    private String content;
    private final Timestamp posting_time;

    private int hot;
    private int shared;
    @TableField(exist = false)
    private Integer User_id;
    @TableField(exist = false)
    private ArrayList<String> categories;
    @TableField(exist = false)
    private City postCity;

    public Post() {
        this.posting_time = new Timestamp(System.currentTimeMillis());
    }

    public Post(String title, String content, String file) {
        this.title = title;
        this.content = content;
        this.posting_time = new Timestamp(System.currentTimeMillis());
        this.file = file;
    }


    public Post(String title, String content, ArrayList<String> categories, int shared, String file) {
        this.title = title;
        this.content = content;
        this.categories = categories;
        this.posting_time = new Timestamp(System.currentTimeMillis());
        this.shared = shared;
        this.file = file;
    }

    public Post(String title, String content, ArrayList<String> categories, String file) {
        this.title = title;
        this.content = content;
        this.categories = categories;
        this.posting_time = new Timestamp(System.currentTimeMillis());
        this.shared = 0;
        this.file = file;
    }
    public Post(@Param("post_id") int post_id, @Param("title") String title,
                @Param("content") String content, @Param("posting_time") Timestamp posting_time,
                @Param("shared") int shared, @Param("hot") int hot, @Param("file") String file) {
        this.post_id = post_id;
        this.title = title;
        this.content = content;
        this.posting_time = posting_time;
        this.shared = shared;
        this.hot = hot;
        this.file = file;
    }

    @Override
    public String toString() {
        return "Post{" +
                "post_id=" + post_id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", posting_time=" + posting_time +
                ", shared=" + shared +
                ", file=" + file +
                '}';
    }

    public Integer getPost_id() {
        return post_id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getShared() {
        return shared;
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
        this.post_id = postid;
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

    public int getHot() {
        return hot;
    }

    public void setHot(int hot) {
        this.hot = hot;
    }

    public void setShared(int shared) {
        this.shared = shared;
    }

    public void setUser_id(Integer user_id) {
        User_id = user_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Post post)) {
            return false;
        }
        return Objects.equals(post_id, post.post_id);
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Override
    public int hashCode() {
        return Objects.hash(post_id);
    }
}
