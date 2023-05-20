package com.cs307.bbsdatabase.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cs307.bbsdatabase.Entity.Reply;
import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReplyMapper extends BaseMapper<Reply> {

    @Select("""
            select r.reply_id,r.content,r.stars,r.anonymous, r.parent_id
                        from userreply ur
                        join replies r on ur.reply_id = r.reply_id
                        where ur.user_name = #{username}
                        limit #{limit} offset #{offset};""")
    ArrayList<Reply>findReplyByUser(String username, int limit, int offset);

    @Select("select * from replies where parent_id = #{reply_id};")
    ArrayList<Reply>findReplyByParent(int reply_id);

    @Insert("insert into replies (content,anonymous,stars) values (#{content},#{anonymous},#{stars});")
    @Options(useGeneratedKeys=true, keyProperty="reply_id", keyColumn="reply_id")
    int replyToPost(Reply reply);

    @Insert("insert into postreply(post_id, reply_id) VALUES (#{post_id},#{reply_id});")
    void postReply(int post_id,int reply_id);

    @Insert("insert into userreply(user_name, reply_id) VALUES (#{username},#{reply_id})")
    void userReply(String username, int reply_id);

    @Insert("insert into replies (content,parent_id, stars,anonymous) values (#{content},#{parent_id},#{stars},#{anonymous});")
    @Options(useGeneratedKeys=true, keyProperty="reply_id", keyColumn="reply_id")
    int replyToReply(Reply reply);
}
