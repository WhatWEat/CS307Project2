package com.cs307.bbsdatabase.Controller;

import com.cs307.bbsdatabase.Entity.Post;
import com.cs307.bbsdatabase.Entity.Reply;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
public class PostController {
    private final int pageSize = 100;
    @GetMapping("/getList")
    //返回第page页的帖子列表，利用sql实现分页查询，简单说就是利用limit语句
    //每页的帖子数量为pageSize
    public ArrayList<Post> PostList(@PathVariable int page){
        return null;
    }
    @PostMapping("/create")
    //发帖子
    public boolean createPost(@RequestBody Post post){
        return false;
    }
    @GetMapping("/findByID")
    //返回帖子id为id的帖子
    public boolean createPost(@PathVariable Integer id){
        return false;
    }
}
