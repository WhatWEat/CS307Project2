package com.cs307.bbsdatabase.Controller;

import com.cs307.bbsdatabase.Entity.Post;
import com.cs307.bbsdatabase.Entity.SearchInfo;
import com.cs307.bbsdatabase.Service.CategoryService;
import com.cs307.bbsdatabase.Service.PostService;
import com.cs307.bbsdatabase.Service.UserService;
import com.cs307.bbsdatabase.Util.Cookies;
import com.cs307.bbsdatabase.Util.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cs307.bbsdatabase.Util.FileManager.getUniqueFileName;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @PostMapping("/searchPost")
    public List<Map<String, String>> searchPost(@RequestBody ArrayList<SearchInfo> searchList,
                                                HttpServletRequest request){
        String username = Cookies.getUsername(request);
        List<String> title = new ArrayList<>();
        List<String> content = new ArrayList<>();
        List<String> category = new ArrayList<>();
        Timestamp start = null;
        Timestamp end = null;
        for (SearchInfo searchInfo : searchList){
            if (!searchInfo.getSelect().equals("4") && searchInfo.getValue().equals("")){
                continue;
            }
            String select = searchInfo.getSelect();
            switch (select) {
                case "1" -> title.add(searchInfo.getValue());
                case "2" -> category.add(searchInfo.getValue());
                case "3" -> content.add(searchInfo.getValue());
                case "4" -> {
                    start = searchInfo.getTimeValue().get(0);
                    end = searchInfo.getTimeValue().get(1);
                }
                default -> {
                }
            }
        }
        List<Post> posts = postService.searchPost(category,title,content,start,end);
        posts = posts.stream().distinct().toList();
        return postService.getMaps(posts,username);
    }

    @GetMapping("/findAllPost/{page}/{pageSize}")
    //返回第page页的帖子列表
    public List<Map<String, String>> findAllPost(@PathVariable int page,
                                                 @PathVariable int pageSize, HttpServletRequest request) {
        String username = Cookies.getUsername(request);
        List<Post> list = postService.findAllPost(page, pageSize,username);
        return postService.getMaps(list, username);
    }
    @GetMapping("/hotList/{page}/{pageSize}")
    //热榜
    public List<Map<String, String>> hotList(@PathVariable int page,
                                                 @PathVariable int pageSize, HttpServletRequest request) {
        String username = Cookies.getUsername(request);
        List<Post> list = postService.hotList(page, pageSize);
        return postService.getMaps(list, username);
    }

    @GetMapping("/findPostByWrite/{page}/{pageSize}")
    //返回第page页的自己发布的帖子列表
    public List<Map<String, String>> findPostByWrite(@PathVariable int page,
                                                     @PathVariable int pageSize, HttpServletRequest request) {
        String username = Cookies.getUsername(request);
        List<Post> list = postService.findPostByWrite(username, page, pageSize);
        return postService.getMaps(list, username);
    }

    @GetMapping("/findPostByLike/{page}/{pageSize}")
    //返回第page页的自己喜欢的帖子列表
    public List<Map<String, String>> findPostByLike(@PathVariable int page,
                                                    @PathVariable int pageSize, HttpServletRequest request) {
        String username = Cookies.getUsername(request);
        List<Post> list = postService.findPostByLike(username, page, pageSize);
        return postService.getMaps(list, username);
    }

    @GetMapping("/findPostByFavorite/{page}/{pageSize}")
    //返回第page页的自己收藏的帖子列表
    public List<Map<String, String>> findPostByFavorite(@PathVariable int page,
                                                        @PathVariable int pageSize, HttpServletRequest request) {
        String username = Cookies.getUsername(request);
        List<Post> list = postService.findPostByFavorite(username, page, pageSize);
        return postService.getMaps(list, username);
    }

    @GetMapping("/findPostByShare/{page}/{pageSize}")
    //返回第page页的自己分享的帖子列表
    public List<Map<String, String>> findPostByShare(@PathVariable int page,
                                                    @PathVariable int pageSize, HttpServletRequest request) {
        String username = Cookies.getUsername(request) ;
        List<Post> list = postService.findPostByShare(username, page, pageSize);
        return postService.getMaps(list,username);
    }

    @PostMapping("/create")
    //发帖子,shared已设置为0
    public boolean createPost(@RequestBody Post post, HttpServletRequest request){
        System.out.println(post);
        System.out.println("Post4444");
        String username = Cookies.getUsername(request);
        String fileName = post.getFile();
        if (fileName!= null){
            post.setFile("Files/"+username+"/"+fileName);
        }else post.setFile("0");
        boolean success = postService.createPost(username, post);
        System.out.println(success);
        return success;
    }

    @GetMapping("/findByID/{post_id}")
    //返回帖子id为id的帖子
    public Map<String, String> findPostById(@PathVariable Integer post_id, HttpServletRequest request) {
        Post post = postService.findPostById(post_id);
        return postService.getMap(post, Cookies.getUsername(request));
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

    @PostMapping("/sharePost/{post_id}")
    //分享帖子
    public void userSharePost(@PathVariable int post_id, HttpServletRequest request) {
        Post beShared = postService.findPostById(post_id);
        Post newPost = new Post(beShared.getTitle(), beShared.getContent(), beShared.getCategories(), beShared.getPost_id(), beShared.getTitle());
        postService.userSharePost(newPost, Cookies.getUsername(request));
        postService.updateHot(2,post_id);
    }

    @PostMapping("/userLikePost/{post_id}")
    //用户进行喜欢操作
    public void userLikePost(@PathVariable int post_id, HttpServletRequest request) {
        postService.userLikePost(post_id, Cookies.getUsername(request));
        postService.updateHot(1,post_id);
    }

    @PostMapping("/userDislikePost/{post_id}")
    //用户取消喜欢
    public void userDislikePost(@PathVariable int post_id, HttpServletRequest request) {
        postService.userDislikePost(post_id, Cookies.getUsername(request));
        postService.updateHot(-1,post_id);
    }

    @PostMapping("/userFavoritePost/{post_id}")
    //用户进行收藏操作
    public void userFavoritePost(@PathVariable int post_id, HttpServletRequest request) {
        postService.userFavoritePost(post_id, Cookies.getUsername(request));
        postService.updateHot(2,post_id);
    }

    @PostMapping("/userCancelFavoritePost/{post_id}")
    //用户取消收藏
    public void userCancelFavoritePost(@PathVariable int post_id, HttpServletRequest request) {
        postService.userCancelFavoritePost(post_id, Cookies.getUsername(request));
        postService.updateHot(-2,post_id);
    }
    @PostMapping("/uploadPic/{username}")
    public void uploadPic(@RequestBody MultipartFile file,@PathVariable String username ){
        try {
            if(file != null)
                FileManager.saveFile(file, username);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
