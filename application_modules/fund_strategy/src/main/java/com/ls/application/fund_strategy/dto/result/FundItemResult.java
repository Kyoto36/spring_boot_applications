package com.ls.application.fund_strategy.dto.result;

import lombok.Data;

/**
 * @author ls
 * @date 2021/12/16 10:49
 * @desc 基金列表的item
 */
@Data
public class FundItemResult {
    private Integer id;
    private String name;
    private String code;
    private Double currentValue; // 当前净值
    private Double lastOperateValue; // 上次操作净值
    private Double gsValue; // 估算净值
    private Double volatility; // 上次操作净值 - 估算净值 的波动率
}
