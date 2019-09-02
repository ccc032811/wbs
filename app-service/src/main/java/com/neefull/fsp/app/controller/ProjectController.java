package com.neefull.fsp.app.controller;


import com.neefull.fsp.app.entity.Project;
import com.neefull.fsp.app.exception.BizException;
import com.neefull.fsp.app.service.IProjectService;
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
     * 查询用户所有项目信息
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
}
