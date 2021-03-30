package com.neefull.fsp.web.sms.controller;

import com.neefull.fsp.web.common.entity.FebsConstant;
import com.neefull.fsp.web.common.utils.FebsUtil;
import com.neefull.fsp.web.sms.entity.Detail;
import com.neefull.fsp.web.sms.entity.Header;
import com.neefull.fsp.web.sms.entity.Scan;
import com.neefull.fsp.web.sms.service.IDetailService;
import com.neefull.fsp.web.sms.service.IHeaderService;
import com.neefull.fsp.web.sms.service.IScanService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: chengchengchu
 * @Date: 2020/11/23  16:58
 */

@Controller("smsView")
public class ViewController {


    @Autowired
    private IHeaderService headerService;
    @Autowired
    private IDetailService detailService;
    @Autowired
    private IScanService scanService;



    //***********************************************系统日志***********************************************************

    @GetMapping(FebsConstant.VIEW_PREFIX + "/log/view")
    public String showQffLog(){
        return FebsUtil.view("sms/log/log");
    }


    //***********************************************DN获取记录*******************************************************

    @GetMapping(FebsConstant.VIEW_PREFIX + "/dn/scan/log")
    @RequiresPermissions("scan:log")
    public String scanLogView(){
        return FebsUtil.view("sms/scan/scan/scanLog");
    }

    //**********************************************对接TMS*******************************************************

    @GetMapping(FebsConstant.VIEW_PREFIX + "/dn/tms")
    @RequiresPermissions("tms:view")
    public String tmsView(){
        return FebsUtil.view("sms/scan/tms/tmsData");
    }

    //**********************************************DN获取查询*******************************************************

    @GetMapping(FebsConstant.VIEW_PREFIX + "/dn/header")
    @RequiresPermissions("header:view")
    public String headerView(){
        return FebsUtil.view("sms/scan/headerdetail/header");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "/sms/header/{delivery}")
    @RequiresPermissions("header:view")
    public String getHeader(@PathVariable String delivery, Model model){
        Header header = headerService.queryHeaderByDelivery(delivery);
        model.addAttribute("header", header);
        return FebsUtil.view("sms/scan/headerdetail/headerShow");
    }


    @GetMapping(FebsConstant.VIEW_PREFIX + "/sms/detail/{delivery}")
    @RequiresPermissions("header:view")
    public String getDetail(@PathVariable String delivery, Model model){
        model.addAttribute("delivery",delivery);
        return FebsUtil.view("sms/scan/headerdetail/detail");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "/sms/detailShow/{id}")
    @RequiresPermissions("header:view")
    public String getDetailShow(@PathVariable Integer id, Model model){
        Detail detail = detailService.getDetailById(id);
        model.addAttribute("detail",detail);
        return FebsUtil.view("sms/scan/headerdetail/detailShow");
    }

    @GetMapping(value = {FebsConstant.VIEW_PREFIX + "/sms/getScanDetail/{delivery}/{matCode}/{batch}",
            FebsConstant.VIEW_PREFIX + "/sms/getScanDetail/{delivery}/{matCode}"})
    public String getScanDetail(@PathVariable String delivery,
                                @PathVariable String matCode,
                                @PathVariable(required = false) String batch,
                                Model model){
        Scan scan = scanService.getScanDetail(delivery,matCode,batch);
        model.addAttribute("scan",scan);
        model.addAttribute("edit","2");
        return FebsUtil.view("sms/scan/scan/scanDetailShow");
    }


    //**********************************************扫描记录查询*******************************************************

    @GetMapping(FebsConstant.VIEW_PREFIX + "/dn/scanHeader")
    @RequiresPermissions("scanHeader:view")
    public String scanView(){
        return FebsUtil.view("sms/scan/scan/scanDetail");
    }


    @GetMapping(FebsConstant.VIEW_PREFIX + "/sms/scanDetailShow/{id}/{edit}")
    @RequiresPermissions("scanHeader:view")
    public String getScanDetailShow(@PathVariable String id,@PathVariable String edit, Model model) {
        Scan scan = scanService.getScanDetailById(id);
        model.addAttribute("scan",scan);
        model.addAttribute("edit",edit);
        return FebsUtil.view("sms/scan/scan/scanDetailShow");
    }

    //**********************************************扫描对比查询*******************************************************

    @GetMapping(FebsConstant.VIEW_PREFIX + "/dn/scan/compare")
    @RequiresPermissions("compare:view")
    public String scanCompare(){
        return FebsUtil.view("sms/scan/compare/compareHeader");
    }



    @GetMapping(FebsConstant.VIEW_PREFIX + "/sms/compareDetail/{delivery}")
    @RequiresPermissions("compare:view")
    public String getCompareDetail(@PathVariable String delivery, Model model) {
        model.addAttribute("deliveryNumber", delivery);
        return FebsUtil.view("sms/scan/compare/compareDetail");
    }


}
