package com.cs307.bbsdatabase.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cs307.bbsdatabase.Entity.Post;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface PostMapper extends BaseMapper<Post> {
//    @Select("select p.post_id, p.title, p.content, p.posting_time from UserWritePost uwp join posts p on uwp.post_id = p.post_id where uwp.user_name = #{username};")
//    ArrayList<Post> findByUser(String username);

    @Select("select * from posts where post_id = #{post_id};")
    Post findPostById(int post_id);

    @Select("""
            select p.post_id, p.title, p.content, p.posting_time, p.shared
            from UserWritePost uwp
            join posts p on uwp.post_id = p.post_id
            where uwp.user_name = #{username}
            limit #{limit} offset #{offset};""")
    List<Post> findPostByWrite(String username,int limit, int offset);

    @Select("select * from posts limit #{limit} offset #{offset};")
    List<Post> findAllPost(int limit,int offset);

    @Select("""
            select p.post_id, p.title, p.content, p.posting_time, p.shared
            from userlikepost ulp
            join posts p on p.post_id = ulp.post_id
            where ulp.user_name = #{username}
            limit #{limit} offset #{offset};""")
    List<Post> findPostByLike(String username, int limit, int offset);

    @Select("""
            select p.post_id, p.title, p.content, p.posting_time, p.shared
            from userfavoritepost ufp
            join posts p on p.post_id = ufp.post_id
            where ufp.user_name = #{username}
            limit #{limit} offset #{offset};""")
    List<Post> findPostByFavorite(String username, int limit, int offset);

    @Select("""
            select p.post_id,p.title,p.content,p.posting_time,p.shared
            from posts p join userwritepost u on p.post_id = u.post_id
            where user_name = #{username} and shared != 0
            limit #{limit} offset #{offset};""")
    List<Post> findPostByShare(String username, int limit, int offset);

    @Select("select count(*) from userlikepost where post_id = #{post_id};")
    int findCountLikeById(int post_id);

    @Select("SELECT EXISTS(SELECT 1 FROM UserLikePost WHERE post_id = #{post_id} AND user_name = #{username});")
    boolean ifLike(int post_id,String username);

    @Select("SELECT EXISTS(SELECT 1 FROM userfavoritepost WHERE post_id = #{post_id} AND user_name = #{username});")
    boolean ifFavorite(int post_id,String username);

    @Select("select user_name from userwritepost where post_id = #{post_id};")
    String findWriter(int post_id);

    //我改了字段名，可能会有错误
    @Insert("insert into posts(title, content, posting_time, shared) " +
            "values(#{title}, #{content}, #{posting_time}, 0)")
    @Options(useGeneratedKeys=true, keyProperty="post_id", keyColumn="post_id")
    int insertPost(Post post);

    @Insert("insert into posts(title, content, posting_time, shared) " +
            "values(#{title}, #{content}, #{posting_time}, #{shared})")
    @Options(useGeneratedKeys=true, keyProperty="post_id", keyColumn="post_id")
    int sharePost(Post post);

    @Insert("insert into UserWritePost(post_id, user_name) VALUES (#{post_id},#{username});")
    void creatPost(int post_id,String username);

    @Insert("insert into UserLikePost(post_id, user_name)\n" +
            "VALUES (#{post_id}, #{username});")
    void userLikePost(int post_id,String username);

    @Insert("insert into userfavoritepost (post_id, user_name) VALUES (#{post_id},#{username});")
    void userFavoritePost(int post_id, String username);

    @Delete("delete from userlikepost where user_name = #{username} and post_id = #{post_id};")
    void userDislikePost(int post_id,String username);

    @Delete("delete from userfavoritepost where user_name = #{username} and post_id = #{post_id};")
    void userCancelFavorite(int post_id, String username);


}
