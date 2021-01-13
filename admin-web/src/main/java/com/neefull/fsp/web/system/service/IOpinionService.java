package com.neefull.fsp.web.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.web.system.entity.Opinion;
import com.neefull.fsp.web.common.entity.OpinionTree;

import java.util.List;


public interface IOpinionService extends IService<Opinion> {
    /**
     * @return 集合
     */
    List<OpinionTree<Opinion>> findOpinion();

    /**
     * 获取表
     */
    List<OpinionTree<Opinion>> findOpinions(Opinion opinion);
    /**
     * 新增
     */
    void createOpinion(Opinion opinion);
    /**
     * 修改
     */
    void updateOpinion(Opinion opinion);
    /**
     * 删除
     */
    void deleteOpinions(String[] opinionIds);

    /**查询
     * @param name
     * @return
     */
    List<Opinion> getOpinions(String name);

    /**
     * @param boxtype
     * @return
     */
    String queryOpionById(String boxtype);

}
