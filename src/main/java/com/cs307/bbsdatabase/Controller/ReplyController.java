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
import com.cs307.bbsdatabase.Util.Cookies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ReplyController {
    @Autowired
    private ReplyService replyService;
    private final int pageSize = 50;
    //    @GetMapping("/replies")
//    public ArrayList<Reply> query(){
//        return replyMapper.selectAllReply();
//    }
    //查询user的所有回复
    @GetMapping("/findReplyByUser/{page}/{username}")
    public List<Map<String,String>> findReplyByUser(@PathVariable int page, @PathVariable String username){
        List<Reply> list = replyService.findReplyByUser(username,page,pageSize);
        return getMaps(list);
    }

    //reply回复post请用这个
    @GetMapping("replyToPost/{post_id}/{content}/{anonymous}")
    public boolean replyToPost(@PathVariable int post_id, @PathVariable String content,@PathVariable Boolean anonymous,
                               HttpServletRequest request){
        return replyService.replyToPost(post_id,content,anonymous, Cookies.getUsername(request));
    }

    //reply回复reply请用这个
    @GetMapping("replyToReply/{parent_id}/{content}/{anonymous}")
    public  boolean replyToReply(@PathVariable int parent_id, @PathVariable String content,@PathVariable Boolean anonymous,
                                 HttpServletRequest request){
        return replyService.replyToReply(parent_id,content,anonymous,Cookies.getUsername(request));
    }

    @GetMapping("findSubReply/{reply_id}")
    public List<Map<String,String>> findSubReply(@PathVariable int reply_id){
        List<Reply> list = replyService.findSubReply(reply_id);
        return getMaps(list);
    }

    private List<Map<String, String>> getMaps(List<Reply> list) {
        List<Map<String,String>> out = new ArrayList<>();
        for (Reply reply : list) {
            Map<String, String> temp = getMap(reply);
            out.add(temp);
        }
        return out;
    }

    private Map<String,String> getMap(Reply reply){
        Map<String,String> out = new HashMap<>();
//        List<Reply> son = replyService.findReplyByParent(reply.getReply_id());
        out.put("content",reply.getContent());
        out.put("stars", String.valueOf(reply.getStars()));
        if (reply.getParent_id() == null){
            out.put("post_id",String.valueOf(reply.getPostID()));
        }else {
            out.put("parent_id",String.valueOf(reply.getParent_id()));
        }
        out.put("anonymous",String.valueOf(reply.isAnonymous()));
        out.put("reply_id",String.valueOf(reply.getReply_id()));
//        out.put("subReplies",son.toString());
        return out;
    }
}
