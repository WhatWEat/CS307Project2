package com.cs307.bbsdatabase.Controller;

import com.cs307.bbsdatabase.Entity.Post;
import com.cs307.bbsdatabase.Util.Cookies;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cs307.bbsdatabase.Service.PostService;
import com.cs307.bbsdatabase.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @GetMapping("/findAllPost/{page}/{pageSize}")
    //返回第page页的帖子列表
    public List<Map<String,String>> findAllPost(@PathVariable int page,@PathVariable int pageSize){
        List<Post> list = postService.findAllPost(page,pageSize);
        return getMaps(list);
    }
    @GetMapping("/findPostByWrite/{page}/{pageSize}")
    //返回第page页的自己发布的帖子列表
    public List<Map<String,String>> findPostByWrite(@PathVariable int page, @PathVariable int pageSize,HttpServletRequest request){
        List<Post> list =  postService.findPostByWrite(Cookies.getUsername(request),page,pageSize);
        return getMaps(list);
    }

    @GetMapping("/findPostByLike/{page}/{pageSize}")
    public List<Map<String,String>> findPostByLike(@PathVariable int page, @PathVariable int pageSize,HttpServletRequest request){
        List<Post> list =  postService.findPostByLike(Cookies.getUsername(request),page,pageSize);
        return getMaps(list);
    }

    @PostMapping("/create/{username}/{title}/{content}")
    //发帖子
    public boolean createPost(@PathVariable String username,@PathVariable String title,@PathVariable String content){
        Post post = new Post(title,content);
        boolean success = postService.createPost(username,title,content);
        System.out.println(post.getPost_id());
        return success;
    }
    @GetMapping("/findByID/{post_id}")
    //返回帖子id为id的帖子
    public Map<String,String> createPost(@PathVariable Integer post_id){
        Post post =  postService.findPostById(post_id);
        return getMap(post);
    }
    @PostMapping("/userLikePost/{post_id}/{username}")
    public void userLikePost(@PathVariable int post_id,@PathVariable String username){
        postService.userLikePost(post_id,username);
    }

    private List<Map<String, String>> getMaps(List<Post> list) {
        List<Map<String,String>> out = new ArrayList<>();
        for (Post post : list) {
            Map<String, String> temp = getMap(post);
            out.add(temp);
        }
        return out;
    }

    private Map<String,String> getMap(Post post) {
        Map<String, String> temp = new HashMap<>();
        temp.put("title", post.getTitle());
        temp.put("post_id", post.getPost_id().toString());
        temp.put("content", post.getContent());
        temp.put("posting_time", post.getPosting_time().toString());
        return temp;
    }
}
