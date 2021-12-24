package com.ls.library.login.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
public class UserLoginDto implements Serializable {

    private static final long serialVersionUID = -3832046191557408399L;

    @NotNull(message = "用户名[userName]不能为空")
    private String userName;

    @NotNull(message = "密码[password]不能为空")
    private String password;
}
