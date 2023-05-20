package com.cs307.bbsdatabase.Util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class Cookies {
    public static String getUsername(HttpServletRequest request){
        return request.getCookies()[0].getValue();
    }
}
