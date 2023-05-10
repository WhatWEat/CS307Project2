package com.cs307.bbsdatabase.Controller;

import com.cs307.bbsdatabase.Entity.User;
import com.cs307.bbsdatabase.Mapper.UserMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/user")
    public List<User> query(){
        List<User> users = userMapper.selectList(null);
        return users;
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
