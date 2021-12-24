package com.ls.application.fund_strategy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ls.application.fund_strategy.dto.param.FundOperateRangeParam;
import com.ls.application.fund_strategy.dto.result.FundOperateRangeResult;
import com.ls.application.fund_strategy.mapper.HoldFundsMapper;
import com.ls.application.fund_strategy.mapper.OperateRangeMapper;
import com.ls.application.fund_strategy.model.CopyRangeToParam;
import com.ls.application.fund_strategy.model.HoldFunds;
import com.ls.application.fund_strategy.model.OperateRange;
import com.ls.application.fund_strategy.service.IOperateRangeService;
import com.ls.common.basics.exception.BizException;
import com.ls.common.basics.result.ApiResponse;
import com.ls.common.basics.service.SuperServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OperateRangeService extends SuperServiceImpl<OperateRangeMapper,OperateRange> implements IOperateRangeService {

    private OperateRangeMapper mOperateRangeMapper;
    private HoldFundsMapper mHoldFundsMapper;

    @Override
    public List<FundOperateRangeResult> getAll() {
        List<OperateRange> operateRanges = mOperateRangeMapper.findAll();
        List<FundOperateRangeResult> fundOperateRanges = new ArrayList<>();
        Map<Integer,List<OperateRange>> map = operateRanges.stream().collect(Collectors.groupingBy(OperateRange::getBelongToFund));
        if(map.size() <= 0){
            return fundOperateRanges;
        }

        Map<Integer,HoldFunds> fundMap = mHoldFundsMapper.getFundsByIds(new ArrayList<>(map.keySet()))
                .stream().collect(Collectors.toMap(HoldFunds::getFundId,holdFunds -> holdFunds));

        map.keySet().forEach(key -> {
            FundOperateRangeResult fundOperateRange = new FundOperateRangeResult();
            int fundId = key;
            HoldFunds fund = fundMap.get(fundId);
            String fundName = "";
            if(fund != null){
                fundName = fund.getFundName();
            }
            if(key == -1) {
                fundName = "通用规则";
            }
            fundOperateRange.setFundId(fundId);
            fundOperateRange.setFundName(fundName);
            fundOperateRange.setOperateRanges(map.get(fundId));
            fundOperateRanges.add(fundOperateRange);
        });

        return fundOperateRanges;
    }

    @Override
    public Boolean addRange(FundOperateRangeParam fundOperateRangeParam) {
        OperateRange range = transform(fundOperateRangeParam);
        range.setRangeId(null);
        Integer temp = mOperateRangeMapper.getHighestSortByFundId(range.getBelongToFund());
        int highestSort = temp == null ? 0 : temp;
        range.setRangeSort(highestSort + 1);
        return save(range);
    }

    private OperateRange transform(FundOperateRangeParam fundOperateRangeParam){
        OperateRange range = new OperateRange();
        range.setRangeId(fundOperateRangeParam.getId());
        range.setRangeName(fundOperateRangeParam.getName());
        range.setUpperLimit(fundOperateRangeParam.getUpperLimit());
        range.setLowerLimit(fundOperateRangeParam.getLowerLimit());
        range.setLowerRate(fundOperateRangeParam.getLowerRate());
        range.setBelongToFund(fundOperateRangeParam.getBelongToFund());
        if(range.getBelongToFund() == null){
            range.setBelongToFund(-1);
        }
        if(range.getLowerRate() == null){
            range.setLowerLimit(1.0);
        }
        return range;
    }

    @Override
    public Boolean updateRange(FundOperateRangeParam fundOperateRangeParam) {
        if(fundOperateRangeParam.getId() == null || mOperateRangeMapper.existsRangeById(fundOperateRangeParam.getId()) <= 0){
            return addRange(fundOperateRangeParam);
        }

        OperateRange range = transform(fundOperateRangeParam);
        if(!Objects.equals(mOperateRangeMapper.getBelongToFundById(range.getRangeId()), range.getBelongToFund())){
            Integer temp = mOperateRangeMapper.getHighestSortByFundId(range.getBelongToFund());
            int highestSort = temp == null ? 0 : temp;
            range.setRangeSort(highestSort + 1);
        }
        return mOperateRangeMapper.updateRange(range) > 0;
    }

    @Transactional
    @Override
    public Boolean deleteRange(Integer rangeId) {
        OperateRange removeRange = lambdaQuery()
                .eq(OperateRange::getRangeId,rangeId)
                .one();
        List<OperateRange> ranges = lambdaQuery()
                .eq(OperateRange::getBelongToFund,removeRange.getBelongToFund())
                .gt(OperateRange::getRangeSort,removeRange.getRangeSort())
                .list();
        ranges.forEach(operateRange -> operateRange.setRangeSort(operateRange.getRangeSort()-1));
        if(!updateBatchById(ranges) || !removeById(rangeId)){
            throw new BizException("删除失败");
        }
        return true;
    }

    @Transactional
    @Override
    public Boolean copyRangeTo(Integer sourceFundId, Integer targetFundId) {
        List<OperateRange> sourceRanges = lambdaQuery()
                .eq(OperateRange::getBelongToFund,sourceFundId)
                .list();
        sourceRanges.forEach(operateRange -> operateRange.setBelongToFund(targetFundId));
        LambdaQueryWrapper<OperateRange> deleteLqw = Wrappers.lambdaQuery();
        remove(deleteLqw.eq(OperateRange::getBelongToFund,targetFundId));
        if(!saveBatch(sourceRanges)){
            throw new BizException("拷贝失败");
        }
        return true;
    }

    @Transactional
    @Override
    public Boolean idSort(String idSort, Integer fundId) {
        String[] idStrs = idSort.split(",");
        List<Integer> ids = Arrays.stream(idStrs).map(Integer::parseInt).collect(Collectors.toList());
        Map<Integer,OperateRange> rangeMap = mOperateRangeMapper.selectBatchIds(ids)
                .stream().collect(Collectors.toMap(OperateRange::getRangeId,operateRange -> operateRange));
        OperateRange range;
        for (int i = 0; i < ids.size(); i++){
            range = rangeMap.get(ids.get(i));
            if(!range.getBelongToFund().equals(fundId)){
                throw new BizException(String.format("区间[%s]与指定的基金ID不匹配",range.getRangeName()));
            }
            range.setRangeSort(i + 1);
        }
        if(!updateBatchById(rangeMap.values())){
            throw new BizException("排序失败");
        }
        return true;
    }

    @Override
    public void noTransactional(int aaa) {
        exec(aaa);
    }

    @Override
    @Transactional
    public void transactional(int aaa) {
        exec(aaa);
    }

    private void exec(int aaa){
        OperateRange range;
        for (int i = 0; i < aaa; i++){
            range = new OperateRange();
            range.setRangeSort(1);
            range.setUpperLimit((double) i);
            range.setLowerLimit(i * 0.01);
            range.setLowerRate((float) i);
            mOperateRangeMapper.insert(range);
        }
    }

    @Override
    public void testInsert(int aaa) {
        mOperateRangeMapper.testInsert(aaa);
    }
}
