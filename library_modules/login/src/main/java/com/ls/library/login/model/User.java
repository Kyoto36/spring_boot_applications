package com.ls.library.login.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户实体
 * @author lang
 * @date 2021-12-14 14:35:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("T_USER")
public class User implements Serializable {

    private static final long serialVersionUID = 7003841303410745161L;

    @TableId(value = "USER_ID", type = IdType.AUTO)
    private Integer userId;

    @TableField(value = "USER_NAME")
    @NotNull(message = "用户名[userName]不能为空")
    private String userName;

    @NotNull(message = "手机号[cellPhone]不能为空")
    @TableField(value = "CELL_PHONE")
    private String cellPhone;

    @TableField(value = "EMAIL")
    private String email;

    @TableField(value = "PASSWORD")
    @NotNull(message = "密码[password]不能为空")
    private String password;

    @TableField(value = "USER_STATUS")
    private Integer userStatus;

    @TableField(value = "CREATE_TIME")
    private LocalDateTime createTime;

    @TableField(value = "UPDATE_TIME")
    private LocalDateTime updateTime;

}
