package com.cs307.bbsdatabase.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cs307.bbsdatabase.Entity.Post;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper extends BaseMapper<Post> {
}
