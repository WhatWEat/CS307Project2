package com.cs307.bbsdatabase.Controller;

import com.cs307.bbsdatabase.Entity.Post;
import com.cs307.bbsdatabase.Entity.Reply;
import com.cs307.bbsdatabase.Entity.User;
import com.cs307.bbsdatabase.Mapper.ReplyMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final int pageSize = 50;

    //    @GetMapping("/replies")
//    public ArrayList<Reply> query(){
//        return replyMapper.selectAllReply();
//    }
    //查询user的所有回复
    @GetMapping("/findReplyByUser/{page}/{pageSize}/{username}")
    public List<Reply> findReplyByUser(@PathVariable int page, @PathVariable int pageSize,HttpServletRequest request) {
        String username = Cookies.getUsername(request);
        List<Reply> list = replyService.findReplyByUser(username, page, pageSize);
        for (Reply member: list){
            setReply(member,username);
        }
        return list;
    }

    @GetMapping("/findTopReplyByPost/{post_id}/{page}/{pageSize}")
    public List<Reply> findTopReplyByPost(@PathVariable int post_id,HttpServletRequest request) {
        String username = Cookies.getUsername(request);
        List<Reply> list = replyService.findTopReplyByPost(post_id);
        for (Reply member: list){
            setTopReply(member,username);
        }
        return list;
    }

    //reply回复post请用这个

    @PostMapping("replyToPost/{post_id}/{content}/{anonymous}")

    public boolean replyToPost(@PathVariable int post_id, @PathVariable String content, @PathVariable Boolean anonymous,
                               HttpServletRequest request) {
        return replyService.replyToPost(post_id, content, anonymous, Cookies.getUsername(request));
    }

    //reply回复reply请用这个
    @PostMapping("replyToReply/{parent_id}/{content}/{anonymous}")
    public boolean replyToReply(@PathVariable int parent_id, @PathVariable String content, @PathVariable Boolean anonymous,
                                HttpServletRequest request) {
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

    private Reply setTopReply(Reply reply, String username) {
        reply.setSon(replyService.findSubReply(reply.getReply_id()));
        setReply(reply, username);
        for (Reply son : reply.getSon()) {
            setSubReply(son, username);
        }
        return reply;
    }

    private Reply setReply(Reply reply, String username) {
//        out.put("content",reply.getContent());
        if (reply.getParent_id() == null) {
            reply.setPostID(replyService.findPostIDByReply(reply.getReply_id()));
        }
        //这里的if分支是为了匿名留的
//        if (reply.isAnonymous()){
//            out.put("anonymous", String.valueOf(reply.isAnonymous()));
//        }else {
        reply.setUsername(replyService.findUserByReply(reply.getReply_id()));
        reply.setIfFollowed(userService.ifFollow(username, replyService.findUserByReply(reply.getReply_id())).equals("true"));

//        }
        reply.setCommentNum(replyService.findCountSubReply(reply.getReply_id()));
        reply.setLike(replyService.countLike(reply.getReply_id()));
//        out.put("subReplies",son.toString());
        return reply;
    }
    private Reply setSubReply(Reply reply, String username) {
//        out.put("content",reply.getContent());
        if (reply.getParent_id() == null) {
            reply.setPostID(replyService.findPostIDByReply(reply.getReply_id()));
        }
        //这里的if分支是为了匿名留的
//        if (reply.isAnonymous()){
//            out.put("anonymous", String.valueOf(reply.isAnonymous()));
//        }else {
        reply.setUsername(replyService.findUserByReply(reply.getReply_id()));
        reply.setIfFollowed(userService.ifFollow(username, replyService.findUserByReply(reply.getReply_id())).equals("true"));

//        }
        reply.setLike(replyService.countLike(reply.getReply_id()));
        reply.setToReply(replyService.findUserByReply(reply.getParent_id()));
//        out.put("subReplies",son.toString());
        return reply;
    }
}
