package com.neefull.fsp.app.controller;


import com.neefull.fsp.app.annotation.AuthToken;
import com.neefull.fsp.app.entity.ProjectSettlement;
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
     * @param projectSettlement
     * @param httpServletRequest
     * @return
     */

    @RequestMapping(value = "/querySettleUsers", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String querySettleUsers(@RequestBody ProjectSettlement projectSettlement, HttpServletRequest httpServletRequest) {
        //long userId = (long) httpServletRequest.getAttribute("userId");
        List<ProjectSettlement> result = projectSettleService.querySettleUsers(projectSettlement);
        if (result.size() > 0) {
            return new FebsResponse().success().data(result).message("查询成功").toJson();
        } else {
            return new FebsResponse().success().data(result).message("未查询到数据").toJson();

        }

    }
}
