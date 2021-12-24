package com.ls.application.fund_strategy.service;

import com.ls.application.fund_strategy.dto.param.FundOperateRangeParam;
import com.ls.application.fund_strategy.dto.result.FundOperateRangeResult;
import com.ls.common.basics.result.ApiResponse;

import java.util.List;

public interface IOperateRangeService {
    List<FundOperateRangeResult> getAll();

    Boolean addRange(FundOperateRangeParam fundOperateRangeParam);

    Boolean updateRange(FundOperateRangeParam fundOperateRangeParam);

    Boolean deleteRange(Integer rangId);

    Boolean copyRangeTo(Integer sourceFundId,Integer targetFundId);

    Boolean idSort(String idSort,Integer fundId);

    void noTransactional(int aaa);

    void transactional(int aaa);

    void testInsert(int aaa);

}
