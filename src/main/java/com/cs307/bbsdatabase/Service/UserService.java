package com.cs307.bbsdatabase.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs307.bbsdatabase.Entity.Post;
import com.cs307.bbsdatabase.Entity.User;
import com.cs307.bbsdatabase.Mapper.PostMapper;
import com.cs307.bbsdatabase.Mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
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
    public User findUserById(String id){
        return userMapper.findById(id);
    }

    public User findUserByPhone(String phone){
        return userMapper.findByPhone(phone);
    }
    public User findUserByUsername(String username){
        return userMapper.findByName(username);
    }

    public boolean checkPassword(String username,String password){
        User user = userMapper.findByName(username);
        return password.equals(user.getPassword());
    }
    public void createUser(String username,  String phone_number,
                            String password){
        User user = new User(username,phone_number,password);
        userMapper.createUser(username,user.getRegistration(),phone_number,user.getId(),password);
    }

    public void userFollowUser(String follower,String be_followed){
        userMapper.userFollowUser(follower,be_followed);
    }

    public void userBlockUser(String blocker,String be_blocked){
        userMapper.userBlockUser(blocker,be_blocked);
    }

    public void userCancelFollow(String follower,String be_followed){
        userMapper.userCancelFollowUser(follower,be_followed);
    }

    public void userCancelBlock(String blocker,String be_blocked){
        userMapper.userCancelBlock(blocker,be_blocked);
    }

    public List<User> findFollowList(String username, int page, int pageSize){
        return userMapper.findFollowList(username,pageSize,(page-1)*pageSize);
    }

    public List<User> findBlockList(String username, int page, int pageSize){
        return userMapper.findBolockList(username,pageSize,(page-1)*pageSize);
    }

    public List<User> findFanList(String username, int page, int pageSize){
        return userMapper.findFanList(username,pageSize,(page-1)*pageSize);
    }

    public int findCountFollowList(String username){
        return userMapper.findCountFollowList(username);
    }

    public int findCountFansList(String username){
        return userMapper.findCountFansList(username);
    }

    public String ifFollow(String user_follower, String user_be_followed){
        if (userMapper.ifFollow(user_follower,user_be_followed)){
            return "true";
        }else {
            return "false";
        }
    }

    public List<User> getUsersByCondition(String user_id, String username, String password) {
        return userMapper.selectUsersByCondition(user_id, username, password);
    }






//    public User findUser(String username)

}
