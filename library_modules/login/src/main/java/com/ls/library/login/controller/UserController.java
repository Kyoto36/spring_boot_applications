package com.ls.library.login.controller;

import com.ls.common.basics.annotation.SysLog;
import com.ls.common.basics.enumerate.BusinessType;
import com.ls.common.basics.result.ApiResponse;
import com.ls.library.login.dto.UserLoginDto;
import com.ls.library.login.model.User;
import com.ls.library.login.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 登录
     * @param dto 登录实体
     * @return
     */
    @SysLog(des = "用户登录", businessType = BusinessType.LOGIN)
    @PostMapping("login")
    public ApiResponse<String> login(@Validated @RequestBody(required = true) UserLoginDto dto){
        return ApiResponse.success(userService.login(dto));
    }

    /**
     * 注册用户
     * @param user
     * @return
     */
    @SysLog(des = "注册用户", businessType = BusinessType.INSERT)
    @PostMapping("register")
    public ApiResponse<Boolean> register(@Validated @RequestBody(required = true) User user){
        return ApiResponse.success(userService.registerUser(user));
    }

    /**
     * 校验用户名是否存在
     * @param userName 用户名
     * @return 返回给前端内容
     */
    @SysLog(des = "查询用户名", businessType = BusinessType.OTHER)
    @GetMapping("checkUserName")
    public ApiResponse<Boolean> checkUserName(@RequestParam("userName") String userName){
        return ApiResponse.success(userService.userNameIfExists(userName));
    }

}
