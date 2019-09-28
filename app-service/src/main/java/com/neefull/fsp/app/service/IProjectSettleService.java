package com.neefull.fsp.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.app.entity.ProjectSettlement;
import com.neefull.fsp.app.entity.ProjectSettlementWapper;

import java.util.List;

/**
 * @author pei.wang
 */
public interface IProjectSettleService extends IService<ProjectSettlement> {

    /**
     * 提交结算申请
     *
     * @param projectSettlementWapper
     * @return
     */
    boolean submitSettlement(ProjectSettlementWapper projectSettlementWapper);

    /**
     * 查询待结算人员
     *
     * @param projectSettlementWapper
     * @return
     */
    List<ProjectSettlement> querySettleUsers(ProjectSettlementWapper projectSettlementWapper);


}
