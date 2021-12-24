package com.ls.application.fund_strategy.dto.result;

import com.ls.application.fund_strategy.model.OperateRange;
import lombok.Data;

import java.util.List;

/**
 * @author ls
 * @date 2021/12/14 11:42
 * @desc 根据基金分类的操作区间
 */
@Data
public class FundOperateRangeResult {
    private Integer fundId;
    private String fundName;
    private List<OperateRange> operateRanges;
}
