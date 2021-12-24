package com.ls.library.login.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Maps;
import com.ls.common.basics.exception.BizException;
import com.ls.common.basics.service.SuperServiceImpl;
import com.ls.library.login.dto.UserLoginDto;
import com.ls.library.login.entity.JwtUserInfo;
import com.ls.library.login.enumerate.CommonStatus;
import com.ls.library.login.mapper.UserMapper;
import com.ls.library.login.model.User;
import com.ls.library.login.service.UserService;
import com.ls.library.login.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl extends SuperServiceImpl<UserMapper, User> implements UserService {

    private UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public boolean userNameIfExists(String userName) {
        Wrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .eq(User::getUserStatus, CommonStatus.NORMAL.getCode())
                .eq(User::getUserName, userName);
        return this.count(wrapper) > 0;
    }

    @Override
    public List<User> getUserByUserName(String userName) {
        Wrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .eq(User::getUserStatus, CommonStatus.NORMAL.getCode())
                .eq(User::getUserName, userName);
        return this.list(wrapper);
    }

    /**
     * <描述功能> 用户注册
     *
     * @param user 用户实体
     * @return com.ls.fundstrategy.model.response.ApiResponse<java.lang.Boolean>
     * @author lang
     * @date 22:18 2021/12/17
     **/
    @Override
    public boolean registerUser(User user) {
        User userBean = new User();
        if(this.userNameIfExists(user.getUserName())){
            throw new BizException("用户名已存在");
        }
        BeanUtil.copyProperties(user, userBean);
        userBean.setCreateTime(LocalDateTime.now());
        userBean.setUpdateTime(LocalDateTime.now());
        userBean.setPassword(DigestUtil.sha256Hex(userBean.getPassword()));
        userBean.setUserStatus(CommonStatus.NORMAL.getCode());
        return this.save(userBean);
    }

    /**
     * <描述功能> 用户登录
     *
     * @param dto 登录用户实体
     * @return com.ls.fundstrategy.model.response.ApiResponse<java.lang.String>
     * @author lang
     * @date 22:19 2021/12/17
     **/
    @Override
    public String login(UserLoginDto dto) {
        final List<User> users = this.getUserByUserName(dto.getUserName());
        if(CollectionUtil.isEmpty(users)){
            throw new BizException("用户名不存在");
        }
        User user = users.get(0);
        if(!DigestUtil.sha256Hex(dto.getPassword()).equals(user.getPassword())){
            throw new BizException("密码错误");
        }
        JwtUserInfo userInfo = new JwtUserInfo(user.getUserId(), user.getUserName(), user.getCellPhone(), Maps.newConcurrentMap());
        final String token = JwtTokenUtil.sign(userInfo, secret);
        return token;
    }
}
