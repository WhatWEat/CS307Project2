package com.cs307.bbsdatabase.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cs307.bbsdatabase.Entity.User;
import com.cs307.bbsdatabase.Mapper.UserMapper;
import com.cs307.bbsdatabase.Service.UserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @GetMapping("/query")
    public List<User> query(){
        QueryWrapper<User> allwrapper = new QueryWrapper<>();
        allwrapper.eq("username","123");

        return userMapper.selectList(allwrapper);
    }
    @PostMapping("/user")
    public String save(User user){
        int i = userMapper.insert(user);
        System.out.println(user);
        if(i > 0)
            return "Successful";
        else
            return "false";
    }
}
