package com.neefull.fsp.web.system.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.web.system.entity.ProjectSettlement;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: fsp
 * @author: xi.chen
 * @create: 2019-09-25 18:34
 **/
public interface IProjectSettlementService extends IService<ProjectSettlement> {

    /**
     * 结算用户
     * @param userId
     * @param projectId
     */
    void settleUserAmount(String userId, String projectId, String projectName, String amount,String settleUserId);

    /**
     * 批量导入结算人员名单
     * @param file
     */
    JSONObject uploadSettleExcel(MultipartFile file, String filename);
}