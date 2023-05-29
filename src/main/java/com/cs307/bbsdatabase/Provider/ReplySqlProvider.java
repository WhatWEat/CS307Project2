package com.cs307.bbsdatabase.Provider;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;


public class ReplySqlProvider {
    public String getReplies(Map<String, Object> params) {
        return new SQL() {{
            SELECT("*");
            FROM("replies");

            if (params.get("reply_id") != null) {
                WHERE("reply_id = #{reply_id}");
            }
            if (params.get("post_id") != null ) {
                WHERE("post_id = #{post_id}");
            }
            if (params.get("content") != null && !((String) params.get("content")).isEmpty()) {
                WHERE("content LIKE CONCAT('%', #{content}, '%')");
            }

            ORDER_BY("replying_time DESC");
        }}.toString();
    }
}
