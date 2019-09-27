package com.neefull.fsp.web.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.neefull.fsp.web.common.controller.BaseController;
import com.neefull.fsp.web.system.service.IProjectSettlementService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: fsp
 * @author: xi.chen
 * @create: 2019-09-27 13:22
 **/
@Slf4j
@Validated
@RestController
@RequestMapping("projectSettle")
public class ProjectSettleController extends BaseController {

    @Autowired
    private IProjectSettlementService projectSettlementService;

    @PostMapping("uploadSettle")
    @RequiresPermissions("project:settle")
    @ResponseBody
    public String uploadSettle(@RequestParam(value = "file", required = false) MultipartFile file){
        JSONObject jsonObject = new JSONObject();
        if(file.isEmpty()){
            jsonObject.put("result","fail");
            jsonObject.put("msg","无效文件!");
        }else{
            String filename = file.getOriginalFilename();
            jsonObject = this.projectSettlementService.uploadSettleExcel(file,filename);
        }
        return jsonObject.toJSONString();
    }
}