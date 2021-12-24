package com.ls.application.fund_strategy.service;

import com.ls.application.fund_strategy.dto.result.FundItemResult;
import com.ls.common.basics.result.ApiResponse;

import java.util.List;

/**
 * @author ls
 * @date 2021/12/14 16:26
 * @desc
 */
public interface IHoldFundsService {
    ApiResponse<List<FundItemResult>> getAll();

    ApiResponse<Boolean> addFund(String fundName,
                                 String fundCode,
                                 Double holdCount,
                                 Double initValue);
}
