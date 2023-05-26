package com.cs307.bbsdatabase.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cs307.bbsdatabase.Entity.Reply;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface ReplyMapper extends BaseMapper<Reply> {

    @Select("""
            select r.reply_id,r.content,r.anonymous, r.parent_id,r.replying_time,r.post_id
                        from userreply ur
                        join replies r on ur.reply_id = r.reply_id
                        where ur.user_name = #{username}
                        limit #{limit} offset #{offset};""")
    @ConstructorArgs({
            @Arg(column = "reply_id", javaType = Integer.class),
            @Arg(column = "content", javaType = String.class),
            @Arg(column = "replying_time", javaType = Timestamp.class),
            @Arg(column = "anonymous", javaType = Boolean.class),
            @Arg(column = "parent_id", javaType = Integer.class),
            @Arg(column = "post_id" , javaType = Integer.class)
    })
    List<Reply>findReplyByUser(String username, int limit, int offset);

    @Select("select * from replies where parent_id = #{reply_id};")
    @ConstructorArgs({
            @Arg(column = "reply_id", javaType = Integer.class),
            @Arg(column = "content", javaType = String.class),
            @Arg(column = "replying_time", javaType = Timestamp.class),
            @Arg(column = "anonymous", javaType = Boolean.class),
            @Arg(column = "parent_id", javaType = Integer.class),
            @Arg(column = "post_id" , javaType = Integer.class)
    })
    List<Reply>findReplyByParent(int reply_id);

    @Insert("insert into replies (content,anonymous,replying_time,post_id) values (#{content},#{anonymous},now(),#{post_id});")
    @Options(useGeneratedKeys=true, keyProperty="reply_id", keyColumn="reply_id")
    int replyToPost(Reply reply);

    @Insert("insert into postreply(post_id, reply_id) VALUES (#{post_id},#{reply_id});")
    void postReply(int post_id,int reply_id);

    @Insert("insert into userreply(user_name, reply_id) VALUES (#{username},#{reply_id})")
    void userReply(String username, int reply_id);

    @Insert("insert into replies (content,parent_id,anonymous,replying_time,post_id) " +
            "values (#{content},#{parent_id},#{anonymous},now(),#{post_id});")
    @Options(useGeneratedKeys=true, keyProperty="reply_id", keyColumn="reply_id")
    int replyToReply(Reply reply);


    @Select("""
            WITH RECURSIVE ReplyTree AS (
              SELECT
                reply_id, content, anonymous, parent_id,replying_time, post_id
              FROM
                Replies
              WHERE
                reply_id <> #{reply_id} and parent_id = #{reply_id}
              UNION
              SELECT
                R.reply_id, R.content, R.anonymous, R.parent_id, R.replying_time, R.post_id
              FROM
                Replies R
                JOIN ReplyTree RT ON R.parent_id = RT.reply_id
            )

            SELECT * FROM ReplyTree;""")
    @ConstructorArgs({
            @Arg(column = "reply_id", javaType = Integer.class),
            @Arg(column = "content", javaType = String.class),
            @Arg(column = "replying_time", javaType = Timestamp.class),
            @Arg(column = "anonymous", javaType = Boolean.class),
            @Arg(column = "parent_id", javaType = Integer.class),
            @Arg(column = "post_id" , javaType = Integer.class)
    })
    List<Reply> findSubReply(int reply_id);

    @Select("""
            SELECT R.*
            FROM Replies R
            INNER JOIN UserReply UR ON R.reply_id = UR.reply_id AND UR.user_name = #{userB}
            INNER JOIN UserLikeReply ULR ON R.reply_id = ULR.reply_id AND ULR.user_name = #{userA};
            """)
    @ConstructorArgs({
            @Arg(column = "reply_id", javaType = Integer.class),
            @Arg(column = "content", javaType = String.class),
            @Arg(column = "replying_time", javaType = Timestamp.class),
            @Arg(column = "anonymous", javaType = Boolean.class),
            @Arg(column = "parent_id", javaType = Integer.class),
            @Arg(column = "post_id" , javaType = Integer.class)
    })
    List<Reply> findUserLikeReplyOfUser(String userA, String userB);

    @Select("""
            select r.parent_id, r.content, r.anonymous,r.replying_time,r.reply_id, r.post_id
            from postreply pr join replies r on r.reply_id = pr.reply_id
            where pr.post_id = #{post_id}
            order by replying_time;""")
    @ConstructorArgs({
            @Arg(column = "reply_id", javaType = Integer.class),
            @Arg(column = "content", javaType = String.class),
            @Arg(column = "replying_time", javaType = Timestamp.class),
            @Arg(column = "anonymous", javaType = Boolean.class),
            @Arg(column = "parent_id", javaType = Integer.class),
            @Arg(column = "post_id" , javaType = Integer.class)
    })
    List<Reply> findTopReplyByPost(int post_id);

    @Select("select post_id from postreply where reply_id = #{reply_id};")
    Integer findPostIDByReply(int reply_id);

    @Select("select * from replies where reply_id = #{reply_id};")
    @ConstructorArgs({
            @Arg(column = "reply_id", javaType = Integer.class),
            @Arg(column = "content", javaType = String.class),
            @Arg(column = "replying_time", javaType = Timestamp.class),
            @Arg(column = "anonymous", javaType = Boolean.class),
            @Arg(column = "parent_id", javaType = Integer.class),
            @Arg(column = "post_id" , javaType = Integer.class)
    })
    Reply findReplyById(int reply_id);

    @Select("""
            WITH RECURSIVE ReplyTree AS (
                          SELECT
                            reply_id, content, anonymous, parent_id,replying_time, post_id
                          FROM
                            Replies
                          WHERE
                            reply_id <> #{reply_id} and parent_id = #{reply_id}
                          UNION
                          SELECT
                            R.reply_id, R.content, R.anonymous, R.parent_id, R.replying_time, R.post_id
                          FROM
                            Replies R
                            JOIN ReplyTree RT ON R.parent_id = RT.reply_id
                        )
                        SELECT count(*) FROM ReplyTree;""")

    int findCountSubReply(int reply_id);

    @Select("select user_name from userreply where reply_id = #{reply_id};")
    String findUserByReply(int reply_id);

    @Insert("insert into userlikereply (reply_id, user_name) VALUES (#{reply_id},#{username});")
    void UserLikeReply(int reply_id, String username);

    @Delete("delete from userlikereply where reply_id = #{reply_id} and user_name = #{username};")
    void UserDislikeReply(int reply_id, String username);

    @Select("SELECT EXISTS(SELECT 1 FROM UserLikeReply WHERE reply_id = #{reply_id} AND user_name = #{username});")
    boolean ifLikeReply(int reply_id, String username);

    @Select("select count(*) from userlikereply where reply_id = #{reply_id};")
    int countLike(int reply);


}
