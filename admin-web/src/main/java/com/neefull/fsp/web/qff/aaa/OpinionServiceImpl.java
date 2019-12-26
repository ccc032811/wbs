/*
package com.neefull.fsp.web.qff.aaa;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.web.qff.entity.Opinion;
import com.neefull.fsp.web.qff.entity.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

*/
/**
 *  查询审批意见
 *
 * @Author: chengchengchu
 * @Date: 2019/11/27  15:58
 *//*


@Service
public class OpinionServiceImpl extends ServiceImpl<OpinionMapper, Opinion> implements IOpinionService {


    @Autowired
    private OpinionMapper opinionMenuDao;

    @Override
    public Integer addOpinion(Opinion opinion) {
        int insert = opinionMenuDao.insert(opinion);
        return insert;
    }

    */
/**获取审批意见
     * @param name
     * @return
     *//*

    @Override
    public List<Opinion> getOpinion(String name)  {

        List<Opinion> opinions = opinionMenuDao.getOpinion(name);
        return opinions;
    }

    @Override
    public Integer deleteOpinionMenu(Integer id) {
        Opinion opinionMenuEntity = new Opinion();
        opinionMenuEntity.setId(id);
        opinionMenuEntity.setStatus(1);
        int update = opinionMenuDao.updateById(opinionMenuEntity);
        return update;
    }

    @Override
    public IPage<Opinion> queryAll(Query query) {
        Page<Opinion> page = new Page<>(query.getPageNum(),query.getPageSize());
        IPage<Opinion> pageInfo = opinionMenuDao.queryAll(page,query);
        return pageInfo;
    }

    @Override
    public List<Opinion> queryByParentId(Integer id) {
        QueryWrapper<Opinion> query= new QueryWrapper<>();
        query.eq("parent_id",id);
        List<Opinion> opinions = opinionMenuDao.selectList(query);
        return opinions;
    }
}
*/
