/*package com.neefull.fsp.web.qff.aaa;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.neefull.fsp.web.common.controller.BaseController;
import com.neefull.fsp.web.common.entity.FebsResponse;
import com.neefull.fsp.web.common.exception.FebsException;
import com.neefull.fsp.web.qff.entity.Opinion;
import com.neefull.fsp.web.qff.entity.Query;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

*//**
 * 查询审批意见操作
 *
 * @Author: chengchengchu
 * @Date: 2019/11/27  15:47
 *//*


@RestController
@RequestMapping("/opinion")
public class OpinionController extends BaseController {

    @Autowired
    private IOpinionService opinionService;


    *//**新增意见单
     * @param opinion
     * @return
     * @throws FebsException
     *//*
    @PostMapping("/add")
    public FebsResponse addOpinion(Opinion opinion) throws FebsException {
        Integer count = opinionService.addOpinion(opinion);
        if(count!=1){
            throw new FebsException("新增意见单失败");
        }
        return new FebsResponse().success();
    }

    *//**根据父级id查询
     * @param id
     * @return
     * @throws FebsException
     *//*
    @GetMapping("query")
    public FebsResponse queryByParentId(@RequestParam("id") Integer id) throws FebsException {
        List<Opinion> list = opinionService.queryByParentId(id);
        if(CollectionUtils.isEmpty(list)){
            throw new FebsException("没有下属信息");
        }
        return new FebsResponse().success().data(list);
    }


    *//**查询所有一级
     * @param query
     * @return
     * @throws FebsException
     *//*
    @GetMapping("/list")
    public FebsResponse queryAll(Query query) throws FebsException {
        IPage<Opinion> opinion = opinionService.queryAll(query);
        Map<String, Object> dataTable = getDataTable(opinion);
        if(opinion==null){
            throw new FebsException("查询所有字典失败");
        }
        return new FebsResponse().success().data(dataTable);
    }

    *//**获取审批意见
     * @param name
     * @return
     *//*
    @GetMapping("/getOpinion/{name}")
    public FebsResponse getOpinion(@PathVariable String name) throws FebsException{
        List<Opinion> opinions = opinionService.getOpinion(name);
        if (CollectionUtils.isEmpty(opinions)){
            throw new FebsException("当前没有所属");
        }
        return new FebsResponse().success().data(opinions);
    }

    *//**删除意见
     * @param id
     * @return
     * @throws FebsException
     *//*
    @GetMapping("/deleteOpinionMenu")
    public FebsResponse deleteOpinionMenu(@RequestParam("id")Integer id) throws FebsException {
        Integer count = opinionService.deleteOpinionMenu(id);
        if(count!=1){
            throw new FebsException("删除意见单失败");
        }
        return new FebsResponse().success();
    }




}*/
