package com.cs307.bbsdatabase.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cs307.bbsdatabase.Entity.Category;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    @Insert("insert into postcategory(categoryid,post_id) values(#{category_id}, #{post_id})")
    int insertPostCat(Integer category_id, Integer post_id);
    @Insert("insert into category(category) values(#{category})")
    @Options(useGeneratedKeys=true, keyProperty="categoryid", keyColumn="categoryid")
    int insertCat(Category cat);
    @Select("select categoryid from category where category = #{category}")
    Integer findCat(String category);
    @Select("select category from category ca join postcategory pc on ca.categoryid = pc.categoryid where pc.post_id = #{post_id}")
    List<String> findCatByPostId(Integer post_id);
}
