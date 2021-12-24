package com.ls.application.fund_strategy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ls.application.fund_strategy.model.HoldFunds;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ls
 * @date 2021/12/14 14:32
 * @desc
 */
@Mapper
public interface HoldFundsMapper extends BaseMapper<HoldFunds> {

    List<HoldFunds> findAll();

    int existsFundByCode(String code);

    List<HoldFunds> getFundsByIds(List<Integer> ids);
}
