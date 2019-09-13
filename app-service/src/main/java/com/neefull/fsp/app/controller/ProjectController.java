package com.neefull.fsp.app.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.neefull.fsp.app.annotation.AuthToken;
import com.neefull.fsp.app.entity.Project;
import com.neefull.fsp.app.entity.ProjectEnrollment;
import com.neefull.fsp.app.entity.ProjectPage;
import com.neefull.fsp.app.service.IProjectEnrService;
import com.neefull.fsp.app.service.IProjectService;
import com.neefull.fsp.common.entity.FebsResponse;
import com.neefull.fsp.common.entity.QueryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目管理
 *
 * @author pei.wang
 */
@Slf4j
@RestController
@RequestMapping("/project")
@Validated
public class ProjectController {
    @Autowired
    IProjectService projectService;
    @Autowired
    IProjectEnrService projectEnrService;

    /**
     * 企业发布项目
     *
     * @param project
     * @param httpServletRequest
     * @return
     */

    @RequestMapping(value = "/publishProject", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String publishProject(@RequestBody Project project, HttpServletRequest httpServletRequest) {
        long userId = (long) httpServletRequest.getAttribute("userId");
        project.setUserId(userId);
        project.setCreateUser(userId);
        project.setCurrentState('0');
        int result = projectService.saveProject(project);
        if (result > 0) {
            return new FebsResponse().success().data(result).message("项目发布成功").toJson();
        } else {
            return new FebsResponse().fail().data(result).message("项目发布失败").toJson();

        }

    }

    /**
     * 企业首页,根据状态展示
     *
     * @param httpServletRequest
     * @return
     * @
     */

    @RequestMapping(value = "/corpHome", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String corpHome(@RequestBody ProjectPage projectPage, HttpServletRequest httpServletRequest) {
        long userId = (long) httpServletRequest.getAttribute("userId");
        //long userId = 9;
        Project project = projectPage.getProject();
        project.setUserId(userId);
        QueryRequest queryRequest = projectPage.getQueryRequest();
        if (null == project || null == queryRequest) {
            return new FebsResponse().fail().data(null).message("参数不合法").toJson();
        }
        Map<String, Object> dataTable = getDataTable(this.projectService.corpHome(project, queryRequest));
        if (0 == (long)dataTable.get("total")) {
            return new FebsResponse().fail().data(null).message("未查询到相关数据").toJson();
        } else {
            return new FebsResponse().success().data(dataTable).message("数据查询成功").toJson();
        }
    }

    /**
     * 个人首页
     *
     * @return
     * @
     */

    @RequestMapping(value = "/personalHome", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String personalHome(@RequestBody ProjectPage projectPage, HttpServletRequest httpServletRequest) {
        Project project = projectPage.getProject();
        QueryRequest queryRequest = projectPage.getQueryRequest();
        if (null == project || null == queryRequest) {
            return new FebsResponse().fail().data(null).message("参数不合法").toJson();
        }
        // long userId = (long) httpServletRequest.getAttribute("userId");
        Map<String, Object> dataTable = getDataTable(this.projectService.personalHome(project, queryRequest));
        if (0 == (long)dataTable.get("total")) {
            return new FebsResponse().fail().data(null).message("未查询到相关数据").toJson();
        } else {
            return new FebsResponse().success().data(dataTable).message("数据查询成功").toJson();
        }
    }

    /**
     * 查询自由职业者报名项目信息
     * 根据用户和项目状态条件筛选，已报名、待完成、待结算、结算完成
     *
     * @return
     * @
     */
    @RequestMapping(value = "/queryEnroProjectWithFreelencer", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    //@AuthToken
    public String queryEnroProjectWithFreelencer(@RequestBody ProjectEnrollment projectEnrollment) {
        //long userId = (long) httpServletRequest.getAttribute("userId");
        //TODO
        long userId = 9;
        //设置报名用户
        projectEnrollment.setUserId(userId);
        if (projectEnrService.saveProjectEnrollment(projectEnrollment) > 0) {
            return new FebsResponse().success().data("").message("报名成功").toJson();
        } else {
            return new FebsResponse().fail().data("").message("报名失败").toJson();
        }
    }


    /**
     * 分页数据封装
     *
     * @param pageInfo
     * @return
     */
    protected Map<String, Object> getDataTable(IPage<?> pageInfo) {
        Map<String, Object> data = new HashMap<>();
        data.put("rows", pageInfo.getRecords());
        data.put("total", pageInfo.getTotal());
        data.put("pages", pageInfo.getPages());
        data.put("size", pageInfo.getSize());
        data.put("current", pageInfo.getCurrent());
        return data;
    }
}
