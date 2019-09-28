package com.neefull.fsp.app.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neefull.fsp.app.entity.ProjectSettlement;
import com.neefull.fsp.app.entity.ProjectSettlementWapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author pei.wang
 */
public interface ProjectSettleMapper extends BaseMapper<ProjectSettlement> {

    List<ProjectSettlement> querySettleUsers(@Param("projectSettlementWapper") ProjectSettlementWapper projectSettlementWapper);

    boolean saveSettleSummary(@Param("projectSettlementWapper") ProjectSettlementWapper projectSettlementWapper);

    ProjectSettlement querySettleModel(@Param("modelId") long modelId, @Param("userId") long userId);
}
