package com.cs307.bbsdatabase.Controller;

import com.cs307.bbsdatabase.Entity.Post;
import com.cs307.bbsdatabase.Service.CategoryService;
import com.cs307.bbsdatabase.Service.PostService;
import com.cs307.bbsdatabase.Service.UserService;
import com.cs307.bbsdatabase.Util.Cookies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @GetMapping("/findAllPost/{page}/{pageSize}")
    //返回第page页的帖子列表
    public List<Map<String, String>> findAllPost(@PathVariable int page,
                                                 @PathVariable int pageSize, HttpServletRequest request) {
        String username = Cookies.getUsername(request);
        List<Post> list = postService.findAllPost(page, pageSize);
        return getMaps(list, username);
    }

    @GetMapping("/findPostByWrite/{page}/{pageSize}")
    //返回第page页的自己发布的帖子列表
    public List<Map<String, String>> findPostByWrite(@PathVariable int page,
                                                     @PathVariable int pageSize, HttpServletRequest request) {
        String username = Cookies.getUsername(request);
        List<Post> list = postService.findPostByWrite(username, page, pageSize);
        return getMaps(list, username);
    }

    @GetMapping("/findPostByLike/{page}/{pageSize}")
    //返回第page页的自己喜欢的帖子列表
    public List<Map<String, String>> findPostByLike(@PathVariable int page,
                                                    @PathVariable int pageSize, HttpServletRequest request) {
        String username = Cookies.getUsername(request);
        List<Post> list = postService.findPostByLike(username, page, pageSize);
        return getMaps(list, username);
    }

    @GetMapping("/findPostByFavorite/{page}/{pageSize}")
    //返回第page页的自己收藏的帖子列表
    public List<Map<String, String>> findPostByFavorite(@PathVariable int page,
                                                        @PathVariable int pageSize, HttpServletRequest request) {
        String username = Cookies.getUsername(request);
        List<Post> list = postService.findPostByFavorite(username, page, pageSize);
        return getMaps(list, username);
    }

    @GetMapping("/findPostByShare/{page}/{pageSize}")
    //返回第page页的自己分享的帖子列表
    public List<Map<String, String>> findPostByShare(@PathVariable int page,
                                                    @PathVariable int pageSize, HttpServletRequest request) {
        String username = Cookies.getUsername(request) ;
        List<Post> list = postService.findPostByShare(username, page, pageSize);
        return getMaps(list,username);
    }

    @PostMapping("/create")
    //发帖子,shared已设置为0
    public boolean createPost(@RequestBody Post post, HttpServletRequest request) {
        boolean success = postService.createPost(Cookies.getUsername(request), post);
        System.out.println(success);
        return success;
    }

    @GetMapping("/findByID/{post_id}")
    //返回帖子id为id的帖子
    public Map<String, String> findPostById(@PathVariable Integer post_id, HttpServletRequest request) {
        Post post = postService.findPostById(post_id);
        return getMap(post, Cookies.getUsername(request));
    }

    @GetMapping("/getTags/{post_id}")
    //返回帖子id为id的帖子的标签,与上面的方法连用
    public List<Map<String, String>> getTags(@PathVariable int post_id) {
        List<Map<String, String>> list = categoryService.getCategories(post_id);
        System.out.println();
        System.out.println(list);
        System.out.println();
        return list;
    }

    @PostMapping("sharePost/{post_id}")
    public void userSharePost(@PathVariable int post_id, HttpServletRequest request) {
        Post beShared = postService.findPostById(post_id);
        Post newPost = new Post(beShared.getTitle(), beShared.getContent(), beShared.getCategories(), beShared.getPost_id());
        postService.userSharePost(newPost, Cookies.getUsername(request));

    }

    @PostMapping("/userLikePost/{post_id}")
    //用户进行喜欢操作
    public void userLikePost(@PathVariable int post_id, HttpServletRequest request) {
        postService.userLikePost(post_id, Cookies.getUsername(request));
    }

    @PostMapping("/userDislikePost/{post_id}")
    //用户取消喜欢
    public void userDislikePost(@PathVariable int post_id, HttpServletRequest request) {
        postService.userDislikePost(post_id, Cookies.getUsername(request));
    }

    @PostMapping("/userFavoritePost/{post_id}")
    //用户进行收藏操作
    public void userFavoritePost(@PathVariable int post_id, HttpServletRequest request) {
        postService.userFavoritePost(post_id, Cookies.getUsername(request));
    }

    @PostMapping("/userCancelFavoritePost/{post_id}")
    //用户取消收藏
    public void userCancelFavoritePost(@PathVariable int post_id, HttpServletRequest request) {
        postService.userCancelFavoritePost(post_id, Cookies.getUsername(request));
    }


    private List<Map<String, String>> getMaps(List<Post> list, String username) {
        List<Map<String, String>> out = new ArrayList<>();
        for (Post post : list) {
            Map<String, String> temp = getMap(post, username);
            out.add(temp);
        }
        return out;
    }

    private Map<String, String> getMap(Post post, String username) {
        Map<String, String> temp = new HashMap<>();
        System.out.println(post);
        temp.put("title", post.getTitle());
        temp.put("id", String.valueOf(post.getPost_id()));
        temp.put("content", post.getContent());
        temp.put("time", post.getPosting_time().toString().substring(0, 19));
        temp.put("shared", String.valueOf(post.getShared()));
        String author = postService.findWriter(post.getPost_id());
        temp.put("author", author);
        temp.put("like", postService.ifLIke(post.getPost_id(), username));
        temp.put("marked", postService.ifFavorite(post.getPost_id(), username));
        temp.put("followed",userService.ifFollow(username,author));
        temp.put("count",String.valueOf(postService.findCountLikeById(post.getPost_id())));
        return temp;
    }
}
