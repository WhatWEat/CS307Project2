package com.cs307.bbsdatabase.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cs307.bbsdatabase.Entity.Reply;
import java.util.ArrayList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReplyMapper extends BaseMapper<Reply> {
    ArrayList<Reply> selectAllReply();
}
