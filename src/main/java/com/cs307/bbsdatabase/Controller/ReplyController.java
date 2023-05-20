package com.cs307.bbsdatabase.Controller;

import com.cs307.bbsdatabase.Entity.Reply;
import com.cs307.bbsdatabase.Entity.User;
import com.cs307.bbsdatabase.Mapper.ReplyMapper;
import java.util.ArrayList;
import java.util.List;

import com.cs307.bbsdatabase.Service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReplyController {
    @Autowired
    private ReplyService replyService;
//    @GetMapping("/replies")
//    public ArrayList<Reply> query(){
//        return replyMapper.selectAllReply();
//    }

    @GetMapping("replyToPost/{post_id}/{content}/{anonymous}")
    public boolean replyToPost(int post_id,String content, Boolean anonymous){
        return replyService.replyToPost(post_id,content,anonymous);
    }
}
