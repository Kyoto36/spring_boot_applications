package com.ls.application.fund_strategy.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 基金操作区间表
 */
@Data
@TableName("operate_range")
public class OperateRange {
    @TableId(value = "range_id",type = IdType.AUTO)
    private Integer rangeId; // 主键ID

    @TableField(value = "range_name")
    private String rangeName; // 区间名称

    @TableField(value = "range_sort")
    private Integer rangeSort; // 排序，每次基金赎回，操作区间就会加1，然后就会根据区间找到对应的排序

    @TableField("upper_limit")
    private Double upperLimit; // 可操作上限，基金涨幅大于等于多少时，可赎回

    @TableField("lower_limit")
    private Double lowerLimit; // 可操作下限，基金跌幅大于等于多少时，可加仓

    @TableField("lower_rate")
    private Float lowerRate; // 操作下限比率，基金跌幅大于等于下限时，应该按几倍跌幅加仓

    @TableField("belong_to_fund")
    private Integer belongToFund; // 多属于某只基金的操作区间，为空表示全部适用
}
