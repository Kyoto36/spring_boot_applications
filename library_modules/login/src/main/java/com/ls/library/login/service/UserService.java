package com.ls.library.login.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ls.library.login.dto.UserLoginDto;
import com.ls.library.login.model.User;

import java.util.List;

/**
 * 用户业务接口
 * @author lang
 * @date 2021-12-14 14:51:56
 */
public interface UserService extends IService<User> {

    /**
     * 查询用户名是否存在
     * @param userName  用户名
     * @return
     */
    boolean userNameIfExists(String userName);

    List<User> getUserByUserName(String userName);

    /**
     * <描述功能> 用户注册
     * @author lang
     * @date 22:18 2021/12/17
     * @param user 用户实体
     * @return com.ls.fundstrategy.model.response.ApiResponse<java.lang.Boolean>
     **/
    boolean registerUser(User user);

    /**
     * <描述功能> 用户登录
     * @author lang
     * @date 22:19 2021/12/17
     * @param dto 登录用户实体
     * @return com.ls.fundstrategy.model.response.ApiResponse<java.lang.String>
     **/
    String login(UserLoginDto dto);
}
