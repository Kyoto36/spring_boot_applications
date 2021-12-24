package com.ls.library.login.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <描述功能>
 *
 * @author Lang
 * @Classname JwtUserInfo
 * @Version 1.0.0
 * @Date 2021/12/17 22:37
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtUserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户标识
     */
    private Integer userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 手机号
     */
    private String cellPhone;

    /**
     * 其他参数
     */
    private Map<String,Object> options = new HashMap<>();
}
