package com.neefull.fsp.app.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.neefull.fsp.app.entity.Project;
import com.neefull.fsp.app.entity.ProjectPage;
import com.neefull.fsp.app.exception.BizException;
import com.neefull.fsp.app.service.IProjectService;
import com.neefull.fsp.common.entity.FebsResponse;
import com.neefull.fsp.common.entity.QueryRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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
@Validated
@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    IProjectService projectService;

    @RequestMapping(value = "/publishProject", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    // @AuthToken
    public String publishProject(@RequestBody Project project, HttpServletRequest httpServletRequest) throws BizException {
        //long userId = (long) httpServletRequest.getAttribute("userId");
        project.setUserId(9);
        project.setCreateUser(9);
        project.setCurrentState('0');
        int result = projectService.saveProject(project);
        if (result > 0) {
            return new FebsResponse().success().data(result).message("项目发布成功").toJson();
        } else {
            return new FebsResponse().fail().data(result).message("项目发布失败").toJson();

        }

    }

    /**
     * 查询企业用户发布的项目
     *
     * @param httpServletRequest
     * @return
     * @throws BizException
     */

    @RequestMapping(value = "/getProjectsByUser", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    //@AuthToken
    public String getProjectsByUser(@RequestBody Project project, HttpServletRequest httpServletRequest) throws BizException {
        // long userId = (long) httpServletRequest.getAttribute("userId");
        long userId = 9;
        List<Project> lst = projectService.getProjectsByUser(userId);
        if (null == lst || lst.size() == 0) {
            return new FebsResponse().fail().data(lst).message("未查询到信息").toJson();
        } else {
            return new FebsResponse().success().data(lst).message("查询完成").toJson();
        }
    }

    /**
     * 个人首页
     *
     * @return
     * @throws BizException
     */

    @RequestMapping(value = "/personalHome", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    //@AuthToken
    public String personalHome(@RequestBody ProjectPage projectPage) throws BizException {
        Project project = projectPage.getProject();
        QueryRequest queryRequest = projectPage.getQueryRequest();
        if(null==project||null==queryRequest)
        {
            return new FebsResponse().fail().data(null).message("参数不合法").toJson();
        }
        // long userId = (long) httpServletRequest.getAttribute("userId");
        Map<String, Object> dataTable = getDataTable(this.projectService.personalHome(project, queryRequest));
        if ("0".equals(dataTable.get("total"))) {
            return new FebsResponse().fail().data(null).message("未查询到相关数据").toJson();
        } else {
            return new FebsResponse().success().data(dataTable).message("数据查询成功").toJson();
        }
    }

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
