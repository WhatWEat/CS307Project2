package com.cs307.bbsdatabase.Controller;

import com.cs307.bbsdatabase.Entity.Post;
import com.cs307.bbsdatabase.Entity.User;
import com.cs307.bbsdatabase.Service.UserService;
import com.cs307.bbsdatabase.Util.Cookies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    //请把你的任务在Service中实现，Controller中只需要调用Service中的方法即可
    @Autowired
    private UserService userService;

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

    @PostMapping("/followUser/{be_followed}")
    public void userFollowUser(@PathVariable String be_followed,HttpServletRequest request){
        userService.userFollowUser(Cookies.getUsername(request),be_followed);
    }

    @PostMapping("cancelFollowUser/{be_followed}")
    public void userCancelFollowUser(@PathVariable String be_followed,HttpServletRequest request){
        userService.userCancelFollow(Cookies.getUsername(request),be_followed);
    }


    @GetMapping("findFollowList/{page}/{pageSize}")

    public List<Map<String, String>> findFollowList(@PathVariable int page,
                                                    @PathVariable int pageSize,HttpServletRequest request){
        String username = Cookies.getUsername(request);
        List<User> followed = userService.findFollowList(username,page,pageSize);
        return getMaps(followed,username);
    }


    @GetMapping("findFanList/{page}/{pageSize}")

    public List<Map<String, String>> findFanList(@PathVariable int page,
                                                    @PathVariable int pageSize,HttpServletRequest request){
        String username = Cookies.getUsername(request);
        List<User> followed = userService.findFanList(username,page,pageSize);
        return getMaps(followed,username);
    }

    public Map<String, String> getMap(User user,String username){
        Map<String, String> temp = new HashMap<>();
        temp.put("username", user.getUsername());
        temp.put("id",user.getId());
        temp.put("time",user.getRegistration().toString().substring(0, 19));
//        temp.put("phone",user.getPhone());
        temp.put("ifBeFollowed",userService.ifFollow(username,user.getUsername()));
        temp.put("countUserFans",String.valueOf(userService.findCountFansList(user.getUsername())));
        temp.put("countUserFollow",String.valueOf(userService.findCountFollowList(user.getUsername())));
        return temp;
    }

    private List<Map<String, String>> getMaps(List<User> list, String username) {
        List<Map<String, String>> out = new ArrayList<>();
        for (User user : list) {
            Map<String, String> temp = getMap(user, username);
            out.add(temp);
        }
        return out;
    }
}
