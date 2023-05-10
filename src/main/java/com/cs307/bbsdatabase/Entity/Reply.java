package com.cs307.bbsdatabase.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
@TableName("Replies")
public class Reply {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String content;
    private int stars;
    private int anonymous;

    public Reply(String content, int stars, int anonymous) {
        this.content = content;
        this.stars = stars;
        this.anonymous = anonymous;
    }

    public Reply(String content, int stars) {
        this.content = content;
        this.stars = stars;
        anonymous = 0;
    }
}
