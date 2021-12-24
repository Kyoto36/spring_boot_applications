package com.ls.application.fund_strategy.dto.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ls
 * @date 2021/12/23
 * @desc
 */
@Data
public class FundOperateRangeParam {
    private Integer id; // 区间ID，新增的时候为空

    @NotNull(message = "区间名称[name]不能为空")
    private String name; // 区间名称

    @NotNull(message = "可操作性上限[upperLimit]不能为空")
    private Double upperLimit; // 可操作上限

    @NotNull(message = "可操作下限[lowerLimit]不能为空")
    private Double lowerLimit; // 可操作下限

    private Float lowerRate; // 下限操作比率
    
    private Integer belongToFund; // 所属基金ID，为空或-1为通用
}
