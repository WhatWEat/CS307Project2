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

    @Autowired
    private UserService userService;



    public boolean replyToPost(int post_id, String content, boolean anonymous,String username){
        Reply reply = new Reply(content,anonymous, post_id);
        int success = replyMapper.replyToPost(reply);
        replyMapper.postReply(post_id,reply.getReply_id());
        replyMapper.userReply(username, reply.getReply_id());
        return true;
    }
    public boolean replyToReply(int parent_id, String content, boolean anonymous,String username){
        Reply reply = new Reply(content,anonymous,replyMapper.findReplyById(parent_id).getPost_id());
        reply.setParent_id(parent_id);
        int success = replyMapper.replyToReply(reply);
        replyMapper.userReply(username,reply.getReply_id());
        return true;
    }

    public List<Reply> findReplyByUser(String username, int page, int pageSize){
        return replyMapper.findReplyByUser(username,pageSize,(page-1)*pageSize);
    }

    public List<Reply> searchReply(Integer reply_id, Integer post_id, String content ){
        return replyMapper.searchReply(reply_id,post_id,content);
    }

    public List<Reply> findUserLikeReplyOfUser(String userA, String userB){
        return replyMapper.findUserLikeReplyOfUser(userA, userB);
    }

    public List<Reply> findSubReply(int reply_id){
        return replyMapper.findSubReply(reply_id);
    }

    public List<Reply> findTopReplyByPost(int post_id){
        return replyMapper.findTopReplyByPost(post_id);
    }

    public Integer findPostIDByReply(int reply_id){
        return replyMapper.findPostIDByReply(reply_id);
    }
    public int findCountSubReply(int reply_id){
        return replyMapper.findCountSubReply(reply_id);
    }

    public String findUserByReply(int reply_id){
        return replyMapper.findUserByReply(reply_id);
    }

    public void UserLikeReply(int reply_id, String username){
        replyMapper.UserLikeReply(reply_id,username);
    }

    public void UserDislikeReply(int reply_id, String username){
        replyMapper.UserDislikeReply(reply_id,username);
    }

    public boolean ifLikeReply(int reply_id, String username){
        return replyMapper.ifLikeReply(reply_id,username);
    }

    public int countLike(int reply_id){
        return replyMapper.countLike(reply_id);
    }

    public Reply findReplyById(int reply_id){
        return replyMapper.findReplyById(reply_id);
    }

    public void setTopReply(Reply reply, String username) {
        reply.setSon(findSubReply(reply.getReply_id()));
        setReply(reply, username);
        for (Reply son : reply.getSon()) {
            setSubReply(son, username);
        }
    }

    public void setReply(Reply reply, String username) {
        reply.setUsername(findUserByReply(reply.getReply_id()));
        reply.setIfFollowed(userService.ifFollow(username, findUserByReply(reply.getReply_id())).equals("true"));

        reply.setCommentNum(findCountSubReply(reply.getReply_id()));
        reply.setLike(countLike(reply.getReply_id()));
    }
    private void setSubReply(Reply reply, String username) {
        reply.setUsername(findUserByReply(reply.getReply_id()));
        reply.setIfFollowed(userService.ifFollow(username, findUserByReply(reply.getReply_id())).equals("true"));

        reply.setLike(countLike(reply.getReply_id()));
        Reply parent = findReplyById(reply.getParent_id());
        if (!parent.isAnonymous()) {
            reply.setToReply(findUserByReply(reply.getParent_id()));
        }else {
            reply.setToReply("匿名");
        }
    }

    public Integer getPostId(int reply_id){
        Reply reply = findReplyById(reply_id);
        while (reply.getParent_id()!=null){
            reply = findReplyById(reply.getParent_id());
        }
        return findPostIDByReply(reply.getReply_id());
    }

}
