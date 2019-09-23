package com.neefull.fsp.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.app.entity.ProjectSettlement;

import java.util.List;

/**
 * @author pei.wang
 */
public interface IProjectSettleService extends IService<ProjectSettlement> {

    List<ProjectSettlement> querySettleUsers(ProjectSettlement projectSettlement);

}
