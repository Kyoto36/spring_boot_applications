package com.ls.application.fund_strategy.model;

import lombok.Data;

/**
 * @author ls
 * @date 2021/12/14 14:23
 * @desc 持有基金表
 */

@Data
public class HoldFunds {
    private Integer fundId; // 主键id
    private String fundCode; // 基金code
    private String fundName; // 基金名称
    private Double initValue; // 初始净值，即加入该条数据时的净值
    private Double holdValue; // 持有净值，每次买入卖出会动态变化
    private Integer operateRange; // 每一次赎回区间加1
    private Double lastOpeateValue; // 上次操作净值，为空即未操作
    private Double currentValue; // 基金当前的最新净值
    private Double holdCount; // 该基金当前持有份额

}
