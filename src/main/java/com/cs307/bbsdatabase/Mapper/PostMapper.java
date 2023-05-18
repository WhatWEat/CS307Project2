package com.cs307.bbsdatabase.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cs307.bbsdatabase.Entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface PostMapper extends BaseMapper<Post> {
    @Select("select p.post_id, p.title, p.content, p.posting_time from UserWritePost uwp join posts p on uwp.post_id = p.post_id where uwp.user_name = #{username};")
    ArrayList<Post> findByUser(String username);
}
