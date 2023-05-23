package com.cs307.bbsdatabase.InterCeptor;

import java.util.Arrays;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        System.out.println(Arrays.toString(cookies));
        System.out.println("拦截"+ (request.getRequestURI().contains("/user") || cookies != null ));
        // 检查session中是否存在登录信息
        // 未登录，进行相应的处理，例如重定向到登录页面
        return true;
    }

}
