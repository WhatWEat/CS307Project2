package com.cs307.bbsdatabase.Provider;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;


public class PostSqlProvider {
    public String getPosts(Map<String, Object> params) {
        return new SQL() {{
            SELECT("*");
            FROM("posts");

            if (params.get("post_id") != null) {
                WHERE("post_id = #{post_id}");
            }
            if (params.get("title") != null && !((String) params.get("title")).isEmpty()) {
                WHERE("title LIKE CONCAT('%', #{title}, '%')");
            }
            if (params.get("content") != null && !((String) params.get("content")).isEmpty()) {
                WHERE("content LIKE CONCAT('%', #{content}, '%')");
            }

            ORDER_BY("posting_time DESC");
        }}.toString();
    }
}