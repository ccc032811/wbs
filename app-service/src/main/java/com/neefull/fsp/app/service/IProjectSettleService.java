package com.neefull.fsp.app.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.app.entity.Project;
import com.neefull.fsp.app.entity.ProjectSettlement;
import com.neefull.fsp.common.entity.QueryRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author pei.wang
 */
public interface IProjectSettleService extends IService<ProjectSettlement> {

    List<ProjectSettlement> querySettleUsers(ProjectSettlement projectSettlement);

}
