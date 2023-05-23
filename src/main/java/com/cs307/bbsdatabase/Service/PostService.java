package com.cs307.bbsdatabase.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs307.bbsdatabase.Entity.Post;
import com.cs307.bbsdatabase.Mapper.PostMapper;
import com.cs307.bbsdatabase.Mapper.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@MapperScan("com.cs307.bbsdatabase")
public class PostService extends ServiceImpl<PostMapper, Post> {
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private CategoryService categoryService;
    public ArrayList<Post> findPostByUser(String username){
        System.out.println(username);
        return postMapper.findByUser(username);
    }

    public boolean createPost(String username, Post post) {
        int success = postMapper.insertPost(post);
        postMapper.creatPost(post.getPost_id(),username);
        categoryService.addCategory(post.getCategories(),post.getPost_id());
        return true;
    }
    public Post findPostById(int post_id){
        return postMapper.findPostById(post_id);
    }

    public List<Post>findPostByWrite(String username, int page, int pageSize){
        return postMapper.findPostByWrite(username,pageSize,(page-1)*pageSize);
    }

    public void userLikePost(int post_id,String username){
        postMapper.userLikePost(post_id,username);
    }

    public List<Post> findPostByLike(String username,int page, int pageSize){
        return postMapper.findPostByLike(username,pageSize,(page-1)*pageSize);
    }

    public List<Post> findAllPost(int page, int pageSize){
        return postMapper.findAllPost(pageSize,(page-1)*pageSize);
    }
}
