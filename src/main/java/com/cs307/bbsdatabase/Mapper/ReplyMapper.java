package com.cs307.bbsdatabase.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cs307.bbsdatabase.Entity.Reply;
import java.util.ArrayList;
import java.util.List;

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
    List<Reply>findReplyByUser(String username, int limit, int offset);

    @Select("select * from replies where parent_id = #{reply_id};")
    List<Reply>findReplyByParent(int reply_id);

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


    @Select("""
            WITH RECURSIVE ReplyTree AS (
              SELECT
                reply_id, content, stars, anonymous, parent_id
              FROM
                Replies
              WHERE
                reply_id <> #{reply_id} and parent_id = #{reply_id}
              UNION
              SELECT
                R.reply_id, R.content, R.stars, R.anonymous, R.parent_id
              FROM
                Replies R
                JOIN ReplyTree RT ON R.parent_id = RT.reply_id
            )

            SELECT * FROM ReplyTree;""")
    List<Reply> findSubReply(int reply_id);
}
