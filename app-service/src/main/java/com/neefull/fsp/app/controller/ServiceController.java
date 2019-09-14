package com.neefull.fsp.app.controller;

import com.neefull.fsp.app.annotation.AuthToken;
import com.neefull.fsp.app.entity.JobDict;
import com.neefull.fsp.app.service.IJobDictService;
import com.neefull.fsp.common.entity.FebsResponse;
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
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    private IJobDictService jobService;

    @RequestMapping(value = "/findService", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String findByMobile(@RequestBody JobDict job) {
        List<JobDict> jobs = jobService.findByLevel(job);
        if (null != jobs && jobs.size()>0) {
            return new FebsResponse().success().data(jobs).message("").toJson();
        } else {
            return new FebsResponse().success().data(jobs).message("未查询到信息").toJson();
        }

    }

}
