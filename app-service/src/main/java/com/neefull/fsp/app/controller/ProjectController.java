package com.neefull.fsp.app.controller;


import com.neefull.fsp.app.annotation.AuthToken;
import com.neefull.fsp.app.entity.Project;
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
        Map<String, Object> dataTable = queryRequest.getDataTable(this.projectService.corpHome(project, queryRequest));
        if (0 == (long) dataTable.get("total")) {
            return new FebsResponse().success().data(dataTable).message("未查询到相关数据").toJson();
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
        long userId = (long) httpServletRequest.getAttribute("userId");
        Project project = projectPage.getProject();
        project.setSignUser(userId);
        QueryRequest queryRequest = projectPage.getQueryRequest();
        if (null == project || null == queryRequest) {
            return new FebsResponse().fail().data(null).message("参数不合法").toJson();
        }
        Map<String, Object> dataTable = queryRequest.getDataTable(this.projectService.personalHome(project, queryRequest));
        if (0 == (long) dataTable.get("total")) {
            return new FebsResponse().success().data(dataTable).message("未查询到相关数据").toJson();
        } else {
            return new FebsResponse().success().data(dataTable).message("数据查询成功").toJson();
        }
    }

    /**
     * 查询项目详细信息
     *
     * @return
     * @
     */

    @RequestMapping(value = "/queryProjectDetail", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String queryProjectDetail(@RequestBody Project project, HttpServletRequest httpServletRequest) {

        long userId = (long) httpServletRequest.getAttribute("userId");
        //long userId = 39;
        if (null == project) {
            return new FebsResponse().fail().data(null).message("参数不合法").toJson();
        }
        project.setSignUser(userId);
        project = projectService.queryProjectDetail(project);
        if (null == project) {
            return new FebsResponse().success().data(project).message("未查询到相关数据").toJson();
        } else {
            return new FebsResponse().success().data(project).message("数据查询成功").toJson();
        }
    }

    /**
     * 根据项目ID开启/关闭项目
     *
     * @return
     * @
     */

    @RequestMapping(value = "/openCloseProject", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String openCloseProject(@RequestBody Project project, HttpServletRequest httpServletRequest) {
        if (projectService.openCloseProject(project) > 0) {
            //更新之后，返回项目的最新信息
            project = projectService.queryProjectDetail(project);
            return new FebsResponse().success().data(project).message("项目操作成功").toJson();
        } else {
            return new FebsResponse().fail().data("").message("项目操作失败").toJson();
        }
    }

    /**
     * 刷新项目
     *
     * @return
     * @
     */

    @RequestMapping(value = "/refreshProject", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    // @AuthToken
    public String refreshProject(@RequestBody Project project, HttpServletRequest httpServletRequest) {
        if (null != (project = projectService.queryProjectDetail(project))) {
            return new FebsResponse().success().data(project).message("项目操作成功").toJson();
        } else {
            return new FebsResponse().fail().data("").message("项目操作失败").toJson();
        }
    }

}
