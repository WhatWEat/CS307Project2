package com.cs307.bbsdatabase.Controller;

import com.cs307.bbsdatabase.Entity.Post;
import com.cs307.bbsdatabase.Entity.Reply;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
public class PostController {
    @GetMapping("/PostList")
    public List<Post> query(){
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(1,"title1",null));
        posts.add(new Post(2,"title2",null));
        System.out.println("返回Post");
        return posts;
    }
}
