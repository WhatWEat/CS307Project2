package com.cs307.bbsdatabase.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
/*
Category的id与内容均为final
 */
public class Category {
    @TableId(type = IdType.AUTO)
    private Integer categoryid;

    private String category;

    public Category() {
    }

    public Category(String category) {
        this.category = category;
    }

    public Category(Integer categoryid, String category) {
        this.categoryid = categoryid;
        this.category = category;
    }

    public void setCategory_id(Integer categoryid) {
        this.categoryid = categoryid;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getCategoryid() {
        return categoryid;
    }


    public String getCategory() {
        return category;
    }

}
