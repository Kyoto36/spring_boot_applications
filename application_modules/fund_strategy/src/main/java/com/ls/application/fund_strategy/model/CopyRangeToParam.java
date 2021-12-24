package com.ls.application.fund_strategy.model;

import lombok.Data;

@Data
public class CopyRangeToParam {
    private Integer sourceFundId;
    private Integer targetFundId;
    private Integer state;
}
