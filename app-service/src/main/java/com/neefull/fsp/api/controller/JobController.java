package com.neefull.fsp.api.controller;

import com.neefull.common.core.entity.FebsResponse;
import com.neefull.common.core.oss.config.QiniuConfig;
import com.neefull.fsp.api.entity.Job;
import com.neefull.fsp.api.service.IJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private QiniuConfig qiniuConfig;

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
