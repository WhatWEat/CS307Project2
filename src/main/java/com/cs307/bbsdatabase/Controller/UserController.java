package com.cs307.bbsdatabase.Controller;

import com.cs307.bbsdatabase.Entity.Post;
import com.cs307.bbsdatabase.Entity.User;
import com.cs307.bbsdatabase.Service.UserService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    //请把你的任务在Service中实现，Controller中只需要调用Service中的方法即可
    @Autowired
    private UserService userService;

    @PostMapping("/reg")
    //实现注册的方法,返回注册成功与否
    //此处若返回false则用户名已被占用，若未被占用则创建用户返回true
    public boolean register(@RequestBody User user){
        if (findUserName(user.getUsername())!=null){
            System.out.println("用户已被注册");
            return false;
        }else
            userService.createUser(user.getUsername(), user.getPhone(), user.getPassword());
        return true;
    }
    @GetMapping("/login")
    //实现登录的方法，返回登录成功与否
    //此处返回1则用户不存在，返回2则密码错误，返回0则登录成功
    public int login(@RequestParam String username, @RequestParam String password) {

        if (findUserName(username) == null){
            System.out.println("用户不存在");
            return 1;
        }else {
            if (userService.checkPassword(username,password)){
                System.out.println("登录成功");
                return 0;
            }else{
                System.out.println("密码错误");
                return 2;
            }
        }
    }
    @GetMapping("/findByID/{id}")
    //通过用户id查找用户，返回用户信息
    public User findById(@PathVariable String id){
        return userService.findUserById(id);
    }
    @GetMapping("/findByName/{name}")
    //通过用户名来查找
    public User findUserName(@PathVariable String name){
        return userService.findUserByUsername(name);
    }
    @GetMapping("/findPostList/{name}")
    //查返回该用户发的所有贴子
    public ArrayList<Post> findPostList(@PathVariable String name){
        if (findUserName(name) == null){
            System.out.println("用户不存在");
            return null;
        }else return userService.findPostByUser(name);
    }
}
