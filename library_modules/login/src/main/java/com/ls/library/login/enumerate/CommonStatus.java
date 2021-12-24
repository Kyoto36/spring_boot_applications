package com.ls.library.login.enumerate;

/**
 * 公用数据状态
 * @author lang
 * @date 2021-12-14 14:57:26
 */
public enum CommonStatus {
    REVOKE(0, "注销"),
    NORMAL(1, "正常"),
    ;

    private int code;
    private String msg;

    CommonStatus(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
