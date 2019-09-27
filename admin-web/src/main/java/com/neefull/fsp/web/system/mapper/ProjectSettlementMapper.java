package com.neefull.fsp.web.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neefull.fsp.web.system.entity.ProjectSettlement;
import org.apache.ibatis.annotations.Param;

/**
 * @program: fsp
 * @author: xi.chen
 * @create: 2019-09-25 18:35
 **/
public interface ProjectSettlementMapper extends BaseMapper<ProjectSettlement> {

    /**
     * 结算用户
     * @param userId
     * @param projectId
     */
    void settleUserAmount(@Param("userId") String userId, @Param("projectId") String projectId);

    /**
     * 根据项目id和人员id查询该用户是否对该项目有过结算标记
     * @param projectId
     * @param userId
     * @return
     */
    String getSettleByProjectIdAndUserId(@Param("projectId") String projectId, @Param("userId") String userId);
}