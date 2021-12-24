package com.ls.library.login.context;

/**
 * <描述功能>
 * 存放常用的一些key
 * @author Lang
 * @Classname BaseContextConstants
 * @Version 1.0.0
 * @Date 2021/12/17 22:36
 **/
public interface BaseContextConstants {

    /**
     * 用户标识
     */
    public static final String JWT_KEY_ACCOUNT = "fuserid";

    /**
     * 用户名称
     */
    public static final String JWT_KEY_USERNAME = "fusername";

    /**
     * 用户手机号
     */
    public static final String JWT_KEY_CELL_PHONE = "fusercellphone";

    /**
     * 用户其他参数
     */
    public static final String JWT_KEY_OPTIONS = "fuseroptions";
}
