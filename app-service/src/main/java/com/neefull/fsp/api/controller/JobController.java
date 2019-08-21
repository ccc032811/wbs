package com.neefull.fsp.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.neefull.common.core.entity.FebsResponse;
import com.neefull.common.core.util.AuthUtils;
import com.neefull.common.core.util.EncryptUtil;
import com.neefull.common.core.util.RegxCheckUtil;
import com.neefull.fsp.api.annotation.AuthToken;
import com.neefull.fsp.api.config.ServConstants;
import com.neefull.fsp.api.entity.Job;
import com.neefull.fsp.api.entity.User;
import com.neefull.fsp.api.entity.UserInfo;
import com.neefull.fsp.api.exception.BizException;
import com.neefull.fsp.api.service.IJobService;
import com.neefull.fsp.api.service.IUserInfoService;
import com.neefull.fsp.api.service.IUserService;
import com.neefull.fsp.api.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author pei.wang
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private IJobService jobService;

    @RequestMapping(value = "/findJobs", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
  //  @AuthToken
    public String findByMobile(@RequestBody Job job) {
        List<Job> jobs = jobService.findByLevel(job);
        if (null != jobs && jobs.size()>0) {
            return new FebsResponse().success().data(jobs).message("").toJson();
        } else {
            return new FebsResponse().fail().message("未查询到信息").toJson();
        }

    }

}
