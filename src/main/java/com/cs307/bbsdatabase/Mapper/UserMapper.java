package com.cs307.bbsdatabase.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cs307.bbsdatabase.Entity.User;

import java.sql.Timestamp;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT * FROM users WHERE user_id = #{id}")
    User findById(String id);
    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByName(String username);

    @Insert("INSERT INTO users (user_id,username,  password, phone_number,registration_time)\n" +
            "VALUES(#{user_id},#{username},#{password},#{phone_number},#{registration_time})")
    void createUser(String user_id,String username,String password,String phone_number
                    ,Timestamp registration_time
     );
//    @Select("")
}
