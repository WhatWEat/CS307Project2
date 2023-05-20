package com.cs307.bbsdatabase.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cs307.bbsdatabase.Entity.Post;
import com.cs307.bbsdatabase.Entity.Reply;
import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface ReplyMapper extends BaseMapper<Reply> {
    ArrayList<Reply> selectAllReply();
    @Insert("insert into replies (content,anonymous) values (#{content},#{anonymous});")
    @Options(useGeneratedKeys=true, keyProperty="reply_id", keyColumn="reply_id")
    int replyToPost(Reply reply);

//    @Insert("insert into postreply(post_id, reply_id) VALUES (#{post_id},#{reply_id});")
//
}
