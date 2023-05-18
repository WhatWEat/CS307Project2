package com.cs307.bbsdatabase.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
/*
Category的id与内容均为final
 */
public class Category {
    @TableId(type = IdType.AUTO)
    private final Integer category_id;

    private final String category;

    public Category(Integer category_id, String category) {
        this.category_id = category_id;
        this.category = category;
    }

    public Integer getCategory_id() {
        return category_id;
    }


    public String getCategory() {
        return category;
    }

}
