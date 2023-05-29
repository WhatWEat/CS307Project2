package com.cs307.bbsdatabase.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cs307.bbsdatabase.Entity.Post;
import com.cs307.bbsdatabase.Provider.PostSqlProvider;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface PostMapper extends BaseMapper<Post> {
//    @Select("select p.post_id, p.title, p.content, p.posting_time from UserWritePost uwp join posts p on uwp.post_id = p.post_id where uwp.user_name = #{username};")
//    ArrayList<Post> findByUser(String username);

//    @Select("select * from posts where post_id = #{post_id};")
//    Post findPostById(int post_id);
    @Select("""
            SELECT *
            FROM posts
            ORDER BY hot DESC, posting_time DESC
            limit #{limit} offset #{offset};""")
    @ConstructorArgs({
            @Arg(column = "post_id", javaType = int.class),
            @Arg(column = "title", javaType = String.class),
            @Arg(column = "content", javaType = String.class),
            @Arg(column = "posting_time", javaType = Timestamp.class),
            @Arg(column = "shared", javaType = int.class),
            @Arg(column = "hot", javaType = int.class)
    })
    List<Post> hotList(int limit, int offset);

    @Select("select * from posts where post_id = #{post_id};")
    @ConstructorArgs({
            @Arg(column = "post_id", javaType = int.class),
            @Arg(column = "title", javaType = String.class),
            @Arg(column = "content", javaType = String.class),
            @Arg(column = "posting_time", javaType = Timestamp.class),
            @Arg(column = "shared", javaType = int.class),
            @Arg(column = "hot", javaType = int.class)
    })
    Post findPostById(int post_id);

    @Select("""
            select p.post_id, p.title, p.content, p.posting_time, p.shared, p.hot
            from UserWritePost uwp
            join posts p on uwp.post_id = p.post_id
            where uwp.user_name = #{username}
            order by posting_time desc
            limit #{limit} offset #{offset};""")
    @ConstructorArgs({
            @Arg(column = "post_id", javaType = int.class),
            @Arg(column = "title", javaType = String.class),
            @Arg(column = "content", javaType = String.class),
            @Arg(column = "posting_time", javaType = Timestamp.class),
            @Arg(column = "shared", javaType = int.class),
            @Arg(column = "hot", javaType = int.class)
    })
    List<Post> findPostByWrite(String username,int limit, int offset);

    @Insert("insert into UserWritePost(post_id, user_name) VALUES (#{post_id},#{username});")
    void creatPost(int post_id,String username);

    @Select("""
            SELECT p.post_id, p.title, p.content, p.posting_time, p.shared, p.hot\s
            FROM posts AS p
            LEFT JOIN UserWritePost AS uwp ON p.post_id = uwp.post_id
            WHERE uwp.user_name IS NULL OR uwp.user_name NOT IN (
              SELECT user_be_blocked FROM UserBlockUser WHERE user_blocker = #{username}
            )
            order by posting_time desc limit #{limit} offset #{offset};""")
    @ConstructorArgs({
            @Arg(column = "post_id", javaType = int.class),
            @Arg(column = "title", javaType = String.class),
            @Arg(column = "content", javaType = String.class),
            @Arg(column = "posting_time", javaType = Timestamp.class),
            @Arg(column = "shared", javaType = int.class),
            @Arg(column = "hot", javaType = int.class)
    })
    List<Post> findAllPost(int limit,int offset,String username);

    @Select("""
            SELECT P.*
            FROM posts P
            INNER JOIN UserWritePost UWP ON P.post_id = UWP.post_id AND UWP.user_name = #{userB}
            INNER JOIN UserLikePost ULP ON P.post_id = ULP.post_id AND ULP.user_name = #{userA};""")
    @ConstructorArgs({
            @Arg(column = "post_id", javaType = int.class),
            @Arg(column = "title", javaType = String.class),
            @Arg(column = "content", javaType = String.class),
            @Arg(column = "posting_time", javaType = Timestamp.class),
            @Arg(column = "shared", javaType = int.class),
            @Arg(column = "hot", javaType = int.class)
    })
    List<Post> findUserLikePostOfUser(String userA, String userB);

    @Select("""
            SELECT P.*
            FROM posts P
            INNER JOIN UserWritePost UWP ON P.post_id = UWP.post_id AND UWP.user_name = #{userB}
            INNER JOIN UserFavoritePost UFP ON P.post_id = UFP.post_id AND UFP.user_name = #{userA};""")
    @ConstructorArgs({
            @Arg(column = "post_id", javaType = int.class),
            @Arg(column = "title", javaType = String.class),
            @Arg(column = "content", javaType = String.class),
            @Arg(column = "posting_time", javaType = Timestamp.class),
            @Arg(column = "shared", javaType = int.class),
            @Arg(column = "hot", javaType = int.class)
    })
    List<Post> findUserFavoritePostOfUser(String userA, String userB);

    @Select("""
            select p.post_id, p.title, p.content, p.posting_time, p.shared, p.hot
            from userlikepost ulp
            join posts p on p.post_id = ulp.post_id
            where ulp.user_name = #{username}
            order by posting_time desc
            limit #{limit} offset #{offset};""")
    @ConstructorArgs({
            @Arg(column = "post_id", javaType = int.class),
            @Arg(column = "title", javaType = String.class),
            @Arg(column = "content", javaType = String.class),
            @Arg(column = "posting_time", javaType = Timestamp.class),
            @Arg(column = "shared", javaType = int.class),
            @Arg(column = "hot", javaType = int.class)
    })
    List<Post> findPostByLike(String username, int limit, int offset);

    @Select("""
            select p.post_id, p.title, p.content, p.posting_time, p.shared, p.hot
            from userfavoritepost ufp
            join posts p on p.post_id = ufp.post_id
            where ufp.user_name = #{username}
            order by posting_time desc
            limit #{limit} offset #{offset};""")
    @ConstructorArgs({
            @Arg(column = "post_id", javaType = int.class),
            @Arg(column = "title", javaType = String.class),
            @Arg(column = "content", javaType = String.class),
            @Arg(column = "posting_time", javaType = Timestamp.class),
            @Arg(column = "shared", javaType = int.class),
            @Arg(column = "hot", javaType = int.class)
    })
    List<Post> findPostByFavorite(String username, int limit, int offset);

    @Select("""
            select p.post_id,p.title,p.content,p.posting_time,p.shared,p.hot
            from posts p join userwritepost u on p.post_id = u.post_id
            where user_name = #{username} and shared != 0
            order by posting_time desc
            limit #{limit} offset #{offset};""")
    @ConstructorArgs({
            @Arg(column = "post_id", javaType = int.class),
            @Arg(column = "title", javaType = String.class),
            @Arg(column = "content", javaType = String.class),
            @Arg(column = "posting_time", javaType = Timestamp.class),
            @Arg(column = "shared", javaType = int.class),
            @Arg(column = "hot", javaType = int.class)
    })
    List<Post> findPostByShare(String username, int limit, int offset);

    @Select("select count(*) from userlikepost where post_id = #{post_id};")
    int findCountLikeById(int post_id);

    @Select("SELECT EXISTS(SELECT 1 FROM UserLikePost WHERE post_id = #{post_id} AND user_name = #{username});")
    boolean ifLike(int post_id,String username);

    @Select("SELECT EXISTS(SELECT 1 FROM userfavoritepost WHERE post_id = #{post_id} AND user_name = #{username});")
    boolean ifFavorite(int post_id,String username);

    @Select("select user_name from userwritepost where post_id = #{post_id};")
    String findWriter(int post_id);

    @SelectProvider(type = PostSqlProvider.class, method = "getPosts")
    @ConstructorArgs({
            @Arg(column = "post_id", javaType = int.class),
            @Arg(column = "title", javaType = String.class),
            @Arg(column = "content", javaType = String.class),
            @Arg(column = "posting_time", javaType = Timestamp.class),
            @Arg(column = "shared", javaType = int.class),
            @Arg(column = "hot", javaType = int.class)
    })
    List<Post> searchPost(@Param("post_id") Integer postId,
                        @Param("title") String title,
                        @Param("content") String content);

    //我改了字段名，可能会有错误
    @Insert("insert into posts(title, content, posting_time, shared,hot) " +
            "values(#{title}, #{content}, #{posting_time}, 0,0)")
    @Options(useGeneratedKeys=true, keyProperty="post_id", keyColumn="post_id")
    int insertPost(Post post);

    @Insert("insert into posts(title, content, posting_time, shared,hot) " +
            "values(#{title}, #{content}, #{posting_time}, #{shared},0)")
    @Options(useGeneratedKeys=true, keyProperty="post_id", keyColumn="post_id")
    int sharePost(Post post);



    @Insert("insert into UserLikePost(post_id, user_name)\n" +
            "VALUES (#{post_id}, #{username});")
    void userLikePost(int post_id,String username);

    @Insert("insert into userfavoritepost (post_id, user_name) VALUES (#{post_id},#{username});")
    void userFavoritePost(int post_id, String username);

    @Delete("delete from userlikepost where user_name = #{username} and post_id = #{post_id};")
    void userDislikePost(int post_id,String username);

    @Delete("delete from userfavoritepost where user_name = #{username} and post_id = #{post_id};")
    void userCancelFavorite(int post_id, String username);

    @Update("update posts set hot = hot + #{changeHot} where post_id = #{post_id};")
    void updateHot(int changeHot, int post_id);

}
