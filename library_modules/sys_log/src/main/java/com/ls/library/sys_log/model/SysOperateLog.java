package com.ls.library.sys_log.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ls.common.basics.enumerate.BusinessType;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <描述功能>
 *
 * @author Lang
 * @Classname OperLog
 * @Version 1.0.0
 * @Date 2021-12-14 09:59:12
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_SYS_OPERATE_LOG")
public class SysOperateLog implements Serializable {

    private static final long serialVersionUID = 1018515248193032524L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 请求IP
     **/
    @TableField(value = "REQUEST_IP")
    @NotNull(message = "请求IP不能为空")
    private String requestIp;

    @TableField(value = "REQUEST_URI")
    private String requestUri;

    /**
     * 请求类型
     * @see BusinessType
     **/
    @TableField(value = "TYPE")
    private String type;

    /**
     * 请求方式
     **/
    @TableField(value = "HTTP_METHOD")
    private String httpMethod;
    /**
     * 请求参数
     **/
    @TableField(value = "REQUEST_PARAMS")
    private String requestParams;

    /**
     * 操作描述
     */
    @TableField(value = "DESCRIPTION")
    private String description;

    /**
     *
     * 异常描述
     */
    @TableField(value = "EX_DESC")
    private String exDesc;

    /**
     * 异常详情信息
     */
    @TableField(value = "EX_DETAIL")
    private String exDetail;

    /**
     * 开始时间
     */
    @TableField(value = "START_TIME")
    private LocalDateTime startTime;

    /**
     * 完成时间
     */
    @TableField(value = "FINISH_TIME")
    private LocalDateTime finishTime;

    /**
     * 消耗时间
     */
    @TableField(value = "CONSUMING_TIME")
    private Long consumingTime;

    /**
     * 执行结果 1成功 0失败
     */
    @TableField(value = "STATE")
    private String state;
}
