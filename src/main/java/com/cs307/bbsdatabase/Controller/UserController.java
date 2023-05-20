package com.cs307.bbsdatabase.Controller;

import com.cs307.bbsdatabase.Entity.Post;
import com.cs307.bbsdatabase.Entity.User;
import com.cs307.bbsdatabase.Service.PostService;
import com.cs307.bbsdatabase.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@RestController
@RequestMapping("/user")
public class UserController {
    //请把你的任务在Service中实现，Controller中只需要调用Service中的方法即可
    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @PostMapping("/reg/{phone}/{username}/{password}")
    //实现注册的方法,返回注册成功与否
    //此处若返回false则用户名已被占用，若未被占用则创建用户返回true
    public boolean register(@PathVariable String phone, @PathVariable String username, @PathVariable String password,
                            HttpServletResponse response) {
        if (findUserName(username) != null) {
            System.out.println("用户名已被占用");
            return false;
        } else if (userService.findUserByPhone(phone) != null) {
            System.out.println("手机号已被注册");
            return false;
        } else {
            System.out.println("注册成功");
            userService.createUser(username, phone, password);
            Cookie cookie = new Cookie("session_id", username);
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        return true;
    }

    @GetMapping("/login/{username}/{password}")
    //实现登录的方法，返回登录成功与否
    //此处返回1则用户不存在，返回2则密码错误，返回0则登录成功
    public boolean login(@PathVariable String username, @PathVariable String password,
                         HttpServletResponse response) {
        if (findUserName(username) == null) {
            System.out.println("用户不存在");
            return false;
        } else {
            if (userService.checkPassword(username, password)) {
                Cookie cookie = new Cookie("session_id", username);
                cookie.setPath("/");
                response.addCookie(cookie);
                System.out.println("登录成功");
                return true;
            } else {
                System.out.println("密码错误");
                return false;
            }
        }
    }

    @GetMapping("/findByID/{id}")
    //通过用户id查找用户，返回用户信息
    public User findById(@PathVariable String id) {
        return userService.findUserById(id);

    }

    @GetMapping("/findByName/{name}")
    //通过用户名来查找
    public User findUserName(@PathVariable String name) {
        return userService.findUserByUsername(name);
    }

    @GetMapping("/findPostList/{name}")
    //查返回该用户发的所有贴子
    public ArrayList<Post> findPostList(@PathVariable String name) {
        System.out.println(name);
        return postService.findPostByUser(name);
    }
}
