package com.neefull.fsp.web.system.controller;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.neefull.fsp.web.common.entity.FebsResponse;
import com.neefull.fsp.web.common.exception.FebsException;
import com.neefull.fsp.web.system.entity.Opinion;
import com.neefull.fsp.web.common.entity.OpinionTree;
import com.neefull.fsp.web.system.service.IOpinionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/opinion")
public class OpinionController {

    @Autowired
    private IOpinionService opinionService;

    /**查询
     * @return
     * @throws FebsException
     */
    @GetMapping("select/tree")
    public List<OpinionTree<Opinion>> getOpinionTree() throws FebsException {
        try {
            return this.opinionService.findOpinion();
        } catch (Exception e) {
            String message = "获取意见失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**查询
     * @param opinion
     * @return
     * @throws FebsException
     */
    @GetMapping("tree")
    public FebsResponse getOpinionTree(Opinion opinion) throws FebsException {
        try {
            List<OpinionTree<Opinion>> opinions = this.opinionService.findOpinions(opinion);
            return new FebsResponse().success().data(opinions);
        } catch (Exception e) {
            String message = "查询意见失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**新增
     * @param opinion
     * @return
     * @throws FebsException
     */
    @PostMapping
    public FebsResponse addOpinion(Opinion opinion) throws FebsException {
        try {
            this.opinionService.createOpinion(opinion);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "新增意见失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**删除
     * @param opinionIds
     * @return
     * @throws FebsException
     */
    @GetMapping("deleteOpinion/{opinionIds}")
    public FebsResponse deleteOpinions(@PathVariable String opinionIds) throws FebsException {
        try {
            String[] ids = opinionIds.split(StringPool.COMMA);
            this.opinionService.deleteOpinions(ids);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "删除意见失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**更新
     * @param opinion
     * @return
     * @throws FebsException
     */
    @PostMapping("update")
    public FebsResponse updateOpinion(Opinion opinion) throws FebsException {
        try {
            this.opinionService.updateOpinion(opinion);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "修改意见失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**查询
     * @param name
     * @return
     */
    @GetMapping("/getOpinion/{name}")
    public FebsResponse getOpinions(@PathVariable String name) throws FebsException {
        try {
            List<Opinion> list = opinionService.getOpinions(name);
            return new FebsResponse().success().data(list);
        } catch (Exception e) {
            String message = "根据名字查询意见失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }


}
