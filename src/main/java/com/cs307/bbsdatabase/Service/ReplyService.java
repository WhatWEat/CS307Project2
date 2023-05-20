package com.cs307.bbsdatabase.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs307.bbsdatabase.Entity.Reply;
import com.cs307.bbsdatabase.Mapper.ReplyMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Service;

@Service
@MapperScan("com.cs307.bbsdatabase")
public class ReplyService extends ServiceImpl<ReplyMapper, Reply> {
    private ReplyMapper replyMapper;

    public boolean replyToPost(int post_id, String content, boolean anonymous){
        Reply reply = new Reply(content,anonymous);
        int success = replyMapper.replyToPost(reply);
        return true;
    }
}
