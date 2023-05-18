package com.cs307.bbsdatabase.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cs307.bbsdatabase.Entity.Post;
import com.cs307.bbsdatabase.Entity.User;
import com.cs307.bbsdatabase.Mapper.UserMapper;
import com.cs307.bbsdatabase.Service.UserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    //请把你的任务在Service中实现，Controller中只需要调用Service中的方法即可
    @Autowired
    private UserService userService;

    @PostMapping("/reg")
    //实现注册的方法,返回注册成功与否
    public boolean register(@RequestBody User user){
        return false;
    }
    @GetMapping("/login")
    //实现登录的方法，返回登录成功与否
    public boolean login(@RequestBody User user){
        return false;
    }
    @GetMapping("/findByID")
    //通过用户id查找用户，返回用户信息
    public User findID(@PathVariable String id){
        System.out.println("方法被调用");
        return null;
    }
    @GetMapping("/findByName")
    //通过用户名来查找
    public User findUserName(@PathVariable String name){
        return null;
    }
    @GetMapping("/findPostList")
    //查返回该用户发的所有贴子
    public ArrayList<Post> findPostList(@PathVariable String name){
        return null;
    }
}
