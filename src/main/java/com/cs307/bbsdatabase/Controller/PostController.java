package com.cs307.bbsdatabase.Controller;

import com.cs307.bbsdatabase.Entity.Category;
import com.cs307.bbsdatabase.Entity.Post;
import com.cs307.bbsdatabase.Mapper.CategoryMapper;
import com.cs307.bbsdatabase.Service.CategoryService;
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
    private CategoryService categoryService;
    @Autowired
    private PostService postService;

    @GetMapping("/findAllPost/{page}/{pageSize}")
    //返回第page页的帖子列表
    public List<Map<String, String>> findAllPost(@PathVariable int page,
        @PathVariable int pageSize) {
        List<Post> list = postService.findAllPost(page, pageSize);
        return getMaps(list);
    }

    @GetMapping("/findPostByWrite/{page}/{pageSize}")
    //返回第page页的自己发布的帖子列表
    public List<Map<String, String>> findPostByWrite(@PathVariable int page,
        @PathVariable int pageSize, HttpServletRequest request) {
        List<Post> list = postService.findPostByWrite(Cookies.getUsername(request), page, pageSize);
        return getMaps(list);
    }

    @GetMapping("/findPostByLike/{page}/{pageSize}")
    //返回第page页的自己喜欢的帖子列表
    public List<Map<String, String>> findPostByLike(@PathVariable int page,
        @PathVariable int pageSize, HttpServletRequest request) {
        List<Post> list = postService.findPostByLike(Cookies.getUsername(request), page, pageSize);
        return getMaps(list);
    }

    @PostMapping("/create")
    //发帖子
    public boolean createPost(@RequestBody Post post, HttpServletRequest request) {
        boolean success = postService.createPost(Cookies.getUsername(request), post);
        System.out.println(success);
        return success;
    }

    @GetMapping("/findByID/{post_id}")
    //返回帖子id为id的帖子
    public Map<String, String> createPost(@PathVariable Integer post_id) {
        Post post = postService.findPostById(post_id);
        return getMap(post);
    }
    @GetMapping("/getTags/{post_id}")
    //返回帖子id为id的帖子的标签,与上面的方法连用
    public List<Map<String,String>> getTags(@PathVariable int post_id) {
        List<Map<String,String>> list = categoryService.getCategories(post_id);
        System.out.println();
        System.out.println(list);
        System.out.println();
        return list;
    }
    @PostMapping("/userLikePost/{post_id}")
    //用户进行喜欢操作
    public void userLikePost(@PathVariable int post_id, HttpServletRequest request) {
        postService.userLikePost(post_id, Cookies.getUsername(request));
    }

    @PostMapping("/userDislikePost/{post_id}")
    //用户取消喜欢
    public void userDislikePost(@PathVariable int post_id, HttpServletRequest request){
        postService.userDislikePost(post_id,Cookies.getUsername(request));
    }

    @PostMapping("/userFavoritePost/{post_id}")
    //用户进行收藏操作
    public void userFavoritePost(@PathVariable int post_id, HttpServletRequest request){
        postService.userFavoritePost(post_id,Cookies.getUsername(request));
    }

    @PostMapping("/userCancelFavoritePost/{post_id}")
    //用户取消收藏
    public void userCancelFavoritePost(@PathVariable int post_id, HttpServletRequest request){
        postService.userCancelFavoritePost(post_id,Cookies.getUsername(request));
    }


    private List<Map<String, String>> getMaps(List<Post> list) {
        List<Map<String, String>> out = new ArrayList<>();
        for (Post post : list) {
            Map<String, String> temp = getMap(post);
            out.add(temp);
        }
        return out;
    }

    private Map<String, String> getMap(Post post) {
        Map<String, String> temp = new HashMap<>();
        temp.put("title", post.getTitle());
        temp.put("id", post.getPost_id().toString());
        temp.put("content", post.getContent());
        temp.put("time", post.getPosting_time().toString().substring(0, 19));
        return temp;
    }
}
