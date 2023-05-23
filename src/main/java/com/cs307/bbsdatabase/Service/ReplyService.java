package com.cs307.bbsdatabase.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs307.bbsdatabase.Entity.Reply;
import com.cs307.bbsdatabase.Mapper.ReplyMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@MapperScan("com.cs307.bbsdatabase")
public class ReplyService extends ServiceImpl<ReplyMapper, Reply> {
    @Autowired
    private ReplyMapper replyMapper;

    public boolean replyToPost(int post_id, String content, boolean anonymous,String username){
        Reply reply = new Reply(content,anonymous);
        int success = replyMapper.replyToPost(reply);
        replyMapper.postReply(post_id,reply.getReply_id());
        replyMapper.userReply(username, reply.getReply_id());
        return true;
    }
    public boolean replyToReply(int parent_id, String content, boolean anonymous,String username){
        Reply reply = new Reply(content,anonymous);
        reply.setParent_id(parent_id);
        int success = replyMapper.replyToReply(reply);
        replyMapper.userReply(username,reply.getReply_id());
        return true;
    }

    public List<Reply> findReplyByUser(String username, int page, int pageSize){
        return replyMapper.findReplyByUser(username,pageSize,(page-1)*pageSize);
    }

    public List<Reply> findReplyByParent(int reply_id){
        return replyMapper.findReplyByParent(reply_id);
    }

    public List<Reply> findSubReply(int reply_id){
        return replyMapper.findSubReply(reply_id);
    }

}
