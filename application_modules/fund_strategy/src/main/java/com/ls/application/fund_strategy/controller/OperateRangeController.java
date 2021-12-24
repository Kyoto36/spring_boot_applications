package com.ls.application.fund_strategy.controller;

import com.ls.application.fund_strategy.dto.param.FundOperateRangeParam;
import com.ls.application.fund_strategy.dto.result.FundOperateRangeResult;
import com.ls.application.fund_strategy.service.IOperateRangeService;
import com.ls.common.basics.result.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ls
 * @date 2021-12-11
 * @desc 基金操作区间接口
 */
@AllArgsConstructor
@RestController
@RequestMapping("/operate-range")
public class OperateRangeController {

    private IOperateRangeService mOperateRangeService;

    /**
     * 查询全部区间
     * @return
     */
    @GetMapping("/getAll")
    public ApiResponse<List<FundOperateRangeResult>> getAll(){
        return mOperateRangeService.getAll();
    }

    /**
     * 新增区间
     * @param fundOperateRangeParam
     * TODO 基金不存在怎么返回
     * @return
     */
    @PutMapping("/addRange")
    public ApiResponse<Boolean> addRange(FundOperateRangeParam fundOperateRangeParam){
        return ApiResponse.success(mOperateRangeService.addRange(fundOperateRangeParam));
    }

    /**
     * 更新区间
     * @param fundOperateRangeParam
     * TODO 基金不存在怎么返回
     * @return
     */
    @PutMapping("/updateRange")
    public ApiResponse<Boolean> updateRange(FundOperateRangeParam fundOperateRangeParam){
        return ApiResponse.success(mOperateRangeService.updateRange(fundOperateRangeParam));
    }

    /**
     * 删除区间
     * @param rangeId
     * @return
     */
    @DeleteMapping("/deleteRange")
    public ApiResponse<Boolean> deleteRange(@RequestParam("rangeId") Integer rangeId){
        return ApiResponse.success(mOperateRangeService.deleteRange(rangeId));
    }


    /**
     * 拷贝一个基金下的区间规则到另一个基金下
     * TODO 另一个基金下的原有规则应该删除，还是无法完成此次操作
     * @param sourceFundId
     * @param targetFundId
     * @return
     */
    @PostMapping("/copyRangeTo")
    public ApiResponse<Boolean> copyRangeTo(
            @RequestParam(name = "sourceFundId",required = false) Integer sourceFundId,
            @RequestParam("targetFundId") Integer targetFundId){
        return ApiResponse.success(mOperateRangeService.copyRangeTo(sourceFundId,targetFundId));
    }

    /**
     * 基金内区间排序
     * @param idSort
     * @param fundId
     * @return
     */
    @PostMapping("/sortRange")
    public ApiResponse<Boolean> sortRange(
            @RequestParam("idSort") String idSort,
            @RequestParam("fundId") Integer fundId){
        return ApiResponse.success(mOperateRangeService.idSort(idSort, fundId));
    }

    @GetMapping("/test")
    public ApiResponse<String> test(){
        System.out.println("-------------------------------------------------transactional-------------------------------------------------------------------------");
        mOperateRangeService.transactional(1000);
        System.out.println("-------------------------------------------------no transactional-------------------------------------------------------------------------");
        mOperateRangeService.noTransactional(1000);
        return ApiResponse.success("");
    }

}
