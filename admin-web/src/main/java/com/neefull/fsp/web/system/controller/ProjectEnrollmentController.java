package com.neefull.fsp.web.system.controller;

import com.neefull.fsp.web.common.annotation.Log;
import com.neefull.fsp.web.common.controller.BaseController;
import com.neefull.fsp.web.common.entity.FebsResponse;
import com.neefull.fsp.web.common.entity.QueryRequest;
import com.neefull.fsp.web.common.exception.FebsException;
import com.neefull.fsp.web.common.utils.FebsUtil;
import com.neefull.fsp.web.system.entity.Project;
import com.neefull.fsp.web.system.entity.ProjectSettlement;
import com.neefull.fsp.web.system.entity.User;
import com.neefull.fsp.web.system.service.IProjectService;
import com.neefull.fsp.web.system.service.IProjectSettlementService;
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
@RequestMapping("projectEnrollment")
public class ProjectEnrollmentController extends BaseController {

    @Autowired
    private IProjectService projectService;

    /**
     * 项目管理-列表查询页面
     * @param project 项目实体类
     * @param request 请求
     * @return 项目信息列表数据
     */
    @GetMapping("list")
    @RequiresPermissions("enrollment:view")
    public FebsResponse projectList(Project project, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(this.projectService.findProjectBySearch(project, request));
        return new FebsResponse().success().data(dataTable);
    }
}