package com.neefull.fsp.web.system.controller;

import com.neefull.fsp.web.common.controller.BaseController;
import com.neefull.fsp.web.common.entity.FebsResponse;
import com.neefull.fsp.web.common.entity.QueryRequest;
import com.neefull.fsp.web.common.exception.FebsException;
import com.neefull.fsp.web.system.entity.Project;
import com.neefull.fsp.web.system.service.IProjectService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}