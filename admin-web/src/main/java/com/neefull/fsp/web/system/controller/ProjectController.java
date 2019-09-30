package com.neefull.fsp.web.system.controller;

import com.neefull.fsp.web.common.annotation.Log;
import com.neefull.fsp.web.common.controller.BaseController;
import com.neefull.fsp.web.common.entity.FebsResponse;
import com.neefull.fsp.web.common.entity.QueryRequest;
import com.neefull.fsp.web.common.exception.FebsException;
import com.neefull.fsp.web.system.entity.Project;
import com.neefull.fsp.web.system.entity.ProjectSettlement;
import com.neefull.fsp.web.system.service.IProjectService;
import com.neefull.fsp.web.system.service.IProjectSettlementService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @program: fsp
 * @author: xi.chen
 * @create: 2019-09-23 15:01
 **/
@Slf4j
@Validated
@RestController
@RequestMapping("project")
public class ProjectController extends BaseController {

    @Autowired
    private IProjectService projectService;
    @Autowired
    private IProjectSettlementService projectSettlementService;

    /**
     * 项目管理-列表查询页面
     * @param project 项目实体类
     * @param request 请求
     * @return 项目信息列表数据
     */
    @GetMapping("list")
    @RequiresPermissions("project:view")
    public FebsResponse projectList(Project project, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(this.projectService.findProjectBySearch(project, request));
        return new FebsResponse().success().data(dataTable);
    }

    /**
     * 查询某项目的结算人员列表
     * @param settlement
     * @param request
     * @return
     */
    @GetMapping("settle")
    @RequiresPermissions("project:settle")
    public FebsResponse settleList(ProjectSettlement settlement, QueryRequest request,String projectId) {
        if(settlement.getProjectId() == 0){
            settlement.setProjectId(Long.valueOf(projectId));
        }
        Map<String, Object> dataTable = getDataTable(this.projectService.findProjectSettleByProjectId(settlement, request));
        return new FebsResponse().success().data(dataTable);
    }

    /**
     * 项目列表页面导出Excel
     * @param queryRequest 请求
     * @param project 查询条件实体
     * @param response response
     * @throws FebsException exception
     */
    @GetMapping("excel")
    @RequiresPermissions("project:export")
    public void export(QueryRequest queryRequest, Project project, HttpServletResponse response) throws FebsException {
        try {
            List<Project> projects = this.projectService.findProjectBySearch(project, queryRequest).getRecords();
            ExcelKit.$Export(Project.class, response).downXlsx(projects, false);
        }catch (Exception e){
            String message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("settleExcel")
    @RequiresPermissions("project:settle")
    public void settleExcel(QueryRequest queryRequest, ProjectSettlement settlement, HttpServletResponse response) throws FebsException {
        try {
            settlement.setState("1");
            List<ProjectSettlement> settlements = this.projectService.findProjectSettleByProjectId(settlement, queryRequest).getRecords();
            ExcelKit.$Export(ProjectSettlement.class, response).downXlsx(settlements, false);
        }catch (Exception e){
            String message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("结算用户")
    @GetMapping("settleUser")
    @RequiresPermissions("project:settle")
    public FebsResponse settleUser(String userId, String projectId) throws FebsException {
        try {
            projectSettlementService.settleUserAmount(userId, projectId);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "操作失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
}