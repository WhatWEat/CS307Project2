package com.cs307.bbsdatabase.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cs307.bbsdatabase.Entity.User;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
//    @Select("select * from users")
//    public List<User> find();
//    @Insert("INSERT INTO users (username, password, phone, registration) VALUES (#{username}, #{password}, #{phone}, #{registration})")
//    int add(User user);
//}
