package com.ls.application.fund_strategy.controller;

import com.ls.application.fund_strategy.dto.result.FundItemResult;
import com.ls.application.fund_strategy.service.IHoldFundsService;
import com.ls.common.basics.result.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ls
 * @date 2021/12/14 16:17
 * @desc
 */
@AllArgsConstructor
@RestController
@RequestMapping("/hold-funds")
public class HoldFundsController {

    private IHoldFundsService mHoldFundsService;

    @GetMapping("/getAll")
    public ApiResponse<List<FundItemResult>> getAll(){
        return mHoldFundsService.getAll();
    }

    @PostMapping("/addFund")
    public ApiResponse<Boolean> addFund(@RequestParam("fundName") String fundName,
                                        @RequestParam("fundCode") String fundCode,
                                        @RequestParam("holdCount") Double holdCount,
                                        @RequestParam("initValue") Double initValue){
        return mHoldFundsService.addFund(fundName, fundCode, holdCount, initValue);
    }
}
