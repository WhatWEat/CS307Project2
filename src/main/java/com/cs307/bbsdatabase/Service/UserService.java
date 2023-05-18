package com.cs307.bbsdatabase.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs307.bbsdatabase.Entity.User;
import com.cs307.bbsdatabase.Mapper.UserMapper;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@Service
@MapperScan("com.cs307.bbsdatabase")
public class UserService extends ServiceImpl<UserMapper, User> {
    @Autowired
    private UserMapper userMapper ;
//    public List<User> getFollow(User user){
//        QueryWrapper<User> a = new QueryWrapper<>();
//        userMapper.select();
//        userMapper.selectList(null);
//    }
    //根据id查找user
    public User findById(String id){
        return userMapper.findById(id);
    }
    public User findByUsername(String username){
        return userMapper.findByName(username);
    }

    public boolean checkPassword(String username,String password){
        User user = userMapper.findByName(username);
        return password.equals(user.getPassword());
    }
    public void createUser(String username,  String phone_number,
                            String password){
        User user = new User(phone_number,username,password);
        userMapper.createUser(user.getId(),username,password,phone_number,user.getRegistration());
    }



//    public User findUser(String username)

}
