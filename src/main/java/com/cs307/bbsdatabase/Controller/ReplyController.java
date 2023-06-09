package com.cs307.bbsdatabase.Controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.cs307.bbsdatabase.Entity.Post;
import com.cs307.bbsdatabase.Entity.Reply;
import com.cs307.bbsdatabase.Entity.User;
import com.cs307.bbsdatabase.Mapper.ReplyMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cs307.bbsdatabase.Service.PostService;
import com.cs307.bbsdatabase.Service.ReplyService;
import com.cs307.bbsdatabase.Service.UserService;
import com.cs307.bbsdatabase.Util.Cookies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/reply")
public class ReplyController {
    @Autowired
    private ReplyService replyService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;
//    private final int pageSize = 50;

    //    @GetMapping("/replies")
//    public ArrayList<Reply> query(){
//        return replyMapper.selectAllReply();
//    }
    //查询user的所有回复
    @GetMapping("/findReplyByUser/{page}/{pageSize}")
    public List<Reply> findReplyByUser(@PathVariable int page, @PathVariable int pageSize,HttpServletRequest request) {
        String username = Cookies.getUsername(request);
        System.out.println(page);
        System.out.println(pageSize);
        System.out.println();
        List<Reply> list = replyService.findReplyByUser(username, page, pageSize);
        for (Reply member: list){
            replyService.setReply(member,username);
//            member.setPostID(getPostId(member.getReply_id()));
        }
        return list;
    }

    @GetMapping("/findTopReplyByPost/{post_id}")
    public List<Reply> findTopReplyByPost(@PathVariable int post_id,HttpServletRequest request) {
        String username = Cookies.getUsername(request);
        List<Reply> list = replyService.findTopReplyByPost(post_id);
        for (Reply member: list){
            replyService.setTopReply(member,username);
        }
        return list;
    }

    @GetMapping("/searchReply/{reply_id}/{post_id}/{content}")
    //查询回复，所有信息允许空，content允许模糊查询（like）
    public List<Reply> searchReply (@RequestParam(required = false)Integer reply_id,
                                   @RequestParam(required = false)Integer post_id,
                                   @RequestParam(required = false)String content,
                                   HttpServletRequest request){
        String username = Cookies.getUsername(request);
        List<Reply> list = replyService.searchReply(reply_id,post_id,content);
        for (Reply member: list){
            replyService.setTopReply(member,username);
        }
        return list;
    }

    @PostMapping("replyToPost/{post_id}/{content}/{anonymous}")
    //reply回复post请用这个
    public boolean replyToPost(@PathVariable int post_id, @PathVariable String content, @PathVariable Boolean anonymous,
                               HttpServletRequest request) {
        postService.updateHot(3,post_id);
        return replyService.replyToPost(post_id, content, anonymous, Cookies.getUsername(request));
    }


    @PostMapping("replyToReply/{parent_id}/{content}/{anonymous}")
    //reply回复reply请用这个
    public boolean replyToReply(@PathVariable int parent_id, @PathVariable String content, @PathVariable Boolean anonymous,
                                HttpServletRequest request) {
        Integer post_id = replyService.getPostId(parent_id);
        postService.updateHot(1,post_id);
        return replyService.replyToReply(parent_id, content, anonymous, Cookies.getUsername(request));
    }

    @GetMapping("findSubReply/{reply_id}/{page}/{pageSize}")
    public List<Reply> findSubReply(@PathVariable int reply_id) {
        return replyService.findSubReply(reply_id);
    }

    @PostMapping("UserLikeReply/{reply_id}")
    public void UserLikeReply(@PathVariable int reply_id, HttpServletRequest request) {
        replyService.UserLikeReply(reply_id, Cookies.getUsername(request));
    }

    @PostMapping("UserDislikeReply/{reply_id}")
    public void UserDislikeReply(@PathVariable int reply_id, HttpServletRequest request) {
        replyService.UserDislikeReply(reply_id, Cookies.getUsername(request));
    }


}
