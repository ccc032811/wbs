package com.neefull.fsp.app.controller;


import com.neefull.fsp.app.annotation.AuthToken;
import com.neefull.fsp.app.entity.ProjectSettlement;
import com.neefull.fsp.app.entity.ProjectSettlementWapper;
import com.neefull.fsp.app.service.IProjectSettleService;
import com.neefull.fsp.common.entity.FebsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 项目管理
 *
 * @author pei.wang
 */
@Slf4j
@RestController
@RequestMapping("/settle")
@Validated
public class ProjectSettleController {
    @Autowired
    IProjectSettleService projectSettleService;

    /**
     * 企业查询特定项目的全部待结算人员
     *
     * @param projectSettlementWapper
     * @param httpServletRequest
     * @return
     */

    @RequestMapping(value = "/querySettleUsers", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String querySettleUsers(@RequestBody ProjectSettlementWapper projectSettlementWapper) {
        List<ProjectSettlement> projectSettlements = projectSettleService.querySettleUsers(projectSettlementWapper);
        if (projectSettlements.size() > 0) {

            for (ProjectSettlement projectSettlement : projectSettlements) {
                //如果当前用户，有一个是模板，则项目显示模板为1
                if (projectSettlement.getIsModel() == 1) {
                    projectSettlement.setIsModel(1);
                    break;
                }
            }
            projectSettlementWapper.setProjectSettlementList(projectSettlements);
            return new FebsResponse().success().data(projectSettlementWapper).message("查询成功").toJson();
        } else {
            return new FebsResponse().success().data(null).message("未查询到数据").toJson();

        }


    }

    /**
     * 企业提交结算
     *
     * @param projectSettlementWapper
     * @param httpServletRequest
     * @return
     **/

    @RequestMapping(value = "/submitSettlement", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String submitSettlement(@RequestBody ProjectSettlementWapper projectSettlementWapper) {
        if (projectSettlementWapper.getProjectSettlementList().size() <= 0) {
            return new FebsResponse().fail().data("").message("确认结算支付失败[结算人员列表为空]").toJson();
        }
        if (projectSettleService.submitSettlement(projectSettlementWapper)) {
            return new FebsResponse().success().data("").message("确认结算支付成功").toJson();
        } else {
            return new FebsResponse().fail().data("").message("确认结算支付失败").toJson();
        }
    }

}