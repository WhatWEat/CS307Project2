package com.cs307.bbsdatabase.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cs307.bbsdatabase.Entity.Post;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface PostMapper extends BaseMapper<Post> {
    @Select("select p.post_id, p.title, p.content, p.posting_time from UserWritePost uwp join posts p on uwp.post_id = p.post_id where uwp.user_name = #{username};")
    ArrayList<Post> findByUser(String username);

    @Select("select * from posts where post_id = #{post_id};")
    Post findPostById(int post_id);

    @Select("""
            select p.post_id, p.title, p.content, p.posting_time
            from UserWritePost uwp
            join posts p on uwp.post_id = p.post_id
            where uwp.user_name = #{username}
            limit #{limit} offset #{offset};""")
    List<Post> findPostByWrite(String username,int limit, int offset);

    @Select("select * from posts limit #{limit} offset #{offset};")
    List<Post> findAllPost(int limit,int offset);

    @Select("""
            select p.post_id, p.title, p.content, p.posting_time
            from userlikepost ulp
                     join posts p on p.post_id = ulp.post_id
            where ulp.user_name = #{username}
            limit #{limit} offset #{offset};""")
    List<Post> findPostByLike(String username, int limit, int offset);

    @Insert("insert into posts(title, content, posting_time) values(#{title}, #{content}, #{posting_time})")
    @Options(useGeneratedKeys=true, keyProperty="post_id", keyColumn="post_id")
    int insertPost(Post post);

    @Insert("insert into UserWritePost(post_id, user_name) VALUES (#{post_id},#{username});")
    void creatPost(int post_id,String username);

    @Insert("insert into UserLikePost(post_id, user_name)\n" +
            "VALUES (#{post_id}, #{username});")
    void userLikePost(int post_id,String username);


}
