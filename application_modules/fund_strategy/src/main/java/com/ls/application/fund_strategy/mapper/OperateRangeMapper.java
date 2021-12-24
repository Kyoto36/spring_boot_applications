package com.ls.application.fund_strategy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ls.application.fund_strategy.model.CopyRangeToParam;
import com.ls.application.fund_strategy.model.OperateRange;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
public interface OperateRangeMapper extends BaseMapper<OperateRange> {

    List<OperateRange> findAll();

    Integer getHighestSortByFundId(int fundId);

    Integer getBelongToFundById(int rangeId);

    int existsRangeById(int rangeId);

    @Transactional
    int updateRange(OperateRange operateRange);

    @Transactional
    int deleteRange(int rangeId);

    @Transactional
    void copyRangeTo(CopyRangeToParam copyRangeToParam);

    void testInsert(int aaa);

}
