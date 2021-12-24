package com.ls.library.login.context;


import com.ls.library.login.entity.JwtUserInfo;

/**
 * <描述功能>
 *
 * @author Lang
 * @Classname BaseContextHandler
 * @Version 1.0.0
 * @Date 2021/12/17 23:33
 **/
public class BaseContextHandler {

    private static final ThreadLocal<JwtUserInfo> JWT_USER_INFO_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * <描述功能> 存储Jwt中解析出来的用户信息
     * @author lang
     **/
    public static JwtUserInfo getJwtUserInfo(){
        return JWT_USER_INFO_THREAD_LOCAL.get();
    }

    /**
     * <描述功能> Jwt中解析出来的用户信息存储到本地
     * @author lang
     **/
    public static void setJwtUserInfo(JwtUserInfo userInfo){
        JWT_USER_INFO_THREAD_LOCAL.set(userInfo);
    }

    public static void remove(){
        JWT_USER_INFO_THREAD_LOCAL.remove();
    }

}
