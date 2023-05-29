package com.cs307.bbsdatabase.Provider;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class UserSqlProvider {

    public String selectUsersByCondition(Map<String, Object> params) {
        return new SQL() {{
            SELECT("*");
            FROM("users");

            if (params.get("user_id") != null) {
                WHERE("user_id = #{user_id}");
            }
            if (params.get("username") != null) {
                WHERE("username like concat('%',#{username},'%')");
            }
            if (params.get("phone_number") != null) {
                WHERE("phone_number = #{phone_number}");
            }
        }}.toString();
    }
}
