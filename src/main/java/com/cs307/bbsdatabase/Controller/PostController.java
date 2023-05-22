package com.cs307.bbsdatabase.Controller;

import com.cs307.bbsdatabase.Entity.Post;
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
    // TODO for zws: 2023/5/21  :
    //  1.返回post带上作者
    //  2.完善post里favorite/share等方法
    //  3.user的频闭与关注


    private final int pageSize = 2;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @GetMapping("/findPostByWrite/{page}/{username}")
    //返回user发布的所有post
    //返回第page页的帖子列表，利用sql实现分页查询，简单说就是利用limit语句
    //每页的帖子数量为pageSize
    public List<Map<String,String>> findPostByWrite(@PathVariable int page,HttpServletRequest request){
        List<Post> list =  postService.findPostByWrite(getCookie(request),page,pageSize);
        return getMaps(list);
    }
    @GetMapping("/findPostByLike/{page}/{username}")
    //返回user like的所有post
    //返回第page页的帖子列表，利用sql实现分页查询，简单说就是利用limit语句
    //每页的帖子数量为pageSize
    public List<Map<String,String>> findPostByLike(@PathVariable int page, HttpServletRequest request){
        List<Post> list =  postService.findPostByLike(getCookie(request),page,pageSize);
        return getMaps(list);
    }
    @GetMapping("/findAllPost/{page}")
    //返回所有帖子
    //每页的帖子数量为pageSize
    public List<Map<String,String>> findAllPost(@PathVariable int page){
        List<Post> list = postService.findAllPost(page,pageSize);
        return getMaps(list);
    }

    @GetMapping("/getCookie")
    public String getCookie(HttpServletRequest request){
        System.out.println(request.getCookies()[0].getValue());
        return request.getCookies()[0].getValue();
    }

    @PostMapping("/create/{username}/{title}/{content}")
    //发帖子
    public boolean createPost(HttpServletRequest request,@PathVariable String title,@PathVariable String content){
        Post post = new Post(title,content);
        boolean success = postService.createPost(getCookie(request),title,content);
        System.out.println(post.getPost_id());
        return success;
    }
    @GetMapping("/findByID/{post_id}")
    //返回帖子id为id的帖子
    public Map<String,String> createPost(@PathVariable Integer post_id){
        Post post =  postService.findPostById(post_id);
        return getMap(post);
    }

    //user进行like操作
    @GetMapping("/userLikePost/{post_id}/{username}")
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
