package com.neefull.fsp.app.controller;


import com.neefull.fsp.app.annotation.AuthToken;
import com.neefull.fsp.app.entity.ProjectEnrollment;
import com.neefull.fsp.app.entity.QueryProjectEncroll;
import com.neefull.fsp.app.mapper.ProjectEnrMapper;
import com.neefull.fsp.app.service.IProjectEnrService;
import com.neefull.fsp.app.service.IProjectService;
import com.neefull.fsp.common.entity.FebsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 报名处理
 *
 * @author pei.wang
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/enroll")
public class EnrollmentController {
    @Autowired
    IProjectService projectService;
    @Autowired
    IProjectEnrService projectEnrService;
    @Autowired
    ProjectEnrMapper projectEnrMapper;

    /**
     * 自由职业者报名项目
     *
     * @return
     * @
     */

    @RequestMapping(value = "/enrollmentProject", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String enrollmentProject(@RequestBody ProjectEnrollment projectEnrollment, HttpServletRequest httpServletRequest) {
        long userId = (long) httpServletRequest.getAttribute("userId");
        //TODO
        //  long userId = 9;
        //设置报名用户
        projectEnrollment.setUserId(userId);
        int result = projectEnrService.saveProjectEnrollment(projectEnrollment);
        if (result > 0) {
            return new FebsResponse().success().data("").message("报名成功,等待企业审核").toJson();
        } else if (result < 0) {
            return new FebsResponse().fail().data("").message("未通过实名认证，暂无法报名项目").toJson();
        } else {
            return new FebsResponse().error().data("").message("服务故障").toJson();
        }
    }


    @RequestMapping(value = "/queryFreelencerEnrollment", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    // @AuthToken
    public String queryFreelencerEnrollment(@RequestBody QueryProjectEncroll queryProjectEncroll, HttpServletRequest httpServletRequest) {
        // long userId = (long) httpServletRequest.getAttribute("userId");
        //TODO
        long userId = 9;
        //设置报名用户
        queryProjectEncroll.setUserId(userId);
        List<QueryProjectEncroll> lst = projectEnrMapper.queryFreelencerEnrollment(queryProjectEncroll);
        if (null == lst || lst.size() <= 0) {
            return new FebsResponse().fail().data("").message("未查询到相关信息").toJson();
        } else {
            return new FebsResponse().success().data(lst).message("查询成功").toJson();
        }
    }

    /**
     * 企业查询相关项目报名用户
     * @param queryProjectEncroll
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/querySignUser", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    // @AuthToken
    public String querySignUser(@RequestBody QueryProjectEncroll queryProjectEncroll, HttpServletRequest httpServletRequest) {
        // long userId = (long) httpServletRequest.getAttribute("userId");
        //TODO
        long userId = 34;
        //设置报名用户
        queryProjectEncroll.setProjectOwner(userId);
        List<QueryProjectEncroll> lst = projectEnrMapper.queryFreelencerEnrollment(queryProjectEncroll);
        if (null == lst || lst.size() <= 0) {
            return new FebsResponse().fail().data("").message("未查询到相关信息").toJson();
        } else {
            return new FebsResponse().success().data(lst).message("查询成功").toJson();
        }
    }
}
