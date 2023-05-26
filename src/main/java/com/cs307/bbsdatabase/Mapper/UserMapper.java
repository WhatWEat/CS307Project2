package com.cs307.bbsdatabase.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cs307.bbsdatabase.Entity.User;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT * FROM users WHERE user_id = #{id}")
    User findById(String id);
    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByName(String username);
    @Select("SELECT * FROM users WHERE phone_number = #{phone_number}")
    User findByPhone(String phone_number);

    @Select("SELECT EXISTS(SELECT 1 FROM userfollowuser " +
            "WHERE user_follower = #{user_follower} AND user_be_followed = #{user_be_followed});")
    boolean ifFollow(String user_follower, String user_be_followed);

    @Select("""
            select u.user_id,u.registration_time,u.phone_number,u.username
            from users u join userfollowuser u2 on u.username = u2.user_be_followed
            where u2.user_follower = #{username}
            order by u.user_id
            limit #{limit} offset #{offset};""")
    List<User> findFollowList(String username, int limit, int offset);

    @Select("""
            select u.user_id,u.registration_time,u.phone_number,u.username
            from users u join userblockuser u2 on u.username = u2.user_be_blocked
            where u2.user_blocker = #{username}
            order by u.user_id
            limit #{limit} offset #{offset};""")
    List<User> findBolockList(String username, int limit, int offset);

    @Select("""
            select u.user_id,u.registration_time,u.phone_number,u.username
            from users u join userfollowuser u2 on u.username = u2.user_follower
            where u2.user_be_followed = #{username}
            order by u.user_id
            limit #{limit} offset #{offset};""")
    List<User> findFanList( String username,int limit, int offset);


    @Select("select count(*) from userfollowuser where user_follower = #{username};")
    int findCountFollowList(String username);

    @Select("select count(*) from userfollowuser where user_be_followed = #{username};")
    int findCountFansList(String username);



    @Insert("INSERT INTO users (username,registration_time, phone_number,user_id,  password)\n" +
            "VALUES(#{username},#{registration_time},#{phone_number},#{user_id},#{password})")
    void createUser(String username,Timestamp registration_time,String phone_number,
                    String user_id,String password
     );

    @Insert("insert into userfollowuser (user_follower, user_be_followed) VALUES (#{follower},#{be_followed});")
    void userFollowUser(String follower,String be_followed);
//    @Select("")

    @Insert("insert into userblockuser (user_blocker, user_be_blocked) VALUES (#{blocker},#{be_blocked});")
    void userBlockUser(String blocker, String be_blocked);

    @Delete("delete from userfollowuser where user_follower = #{follower} and user_be_followed = #{be_followed};")
    void userCancelFollowUser(String follower,String be_followed);

    @Delete("delete from userblockuser where user_blocker = #{blocker} and user_be_blocked = #{be_blocked};")
    void userCancelBlock(String blocker, String be_blocked);



}
