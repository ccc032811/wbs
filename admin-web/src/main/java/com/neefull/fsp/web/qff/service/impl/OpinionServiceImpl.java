package com.neefull.fsp.web.qff.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.web.common.utils.TreeUtil;
import com.neefull.fsp.web.qff.entity.Opinion;
import com.neefull.fsp.web.qff.entity.OpinionTree;
import com.neefull.fsp.web.qff.mapper.OpinionMapper;
import com.neefull.fsp.web.qff.service.IOpinionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class OpinionServiceImpl extends ServiceImpl<OpinionMapper, Opinion> implements IOpinionService {

    @Autowired
    private OpinionMapper opinionMapper;

    @Override
    public List<OpinionTree<Opinion>> findOpinion() {
        List<Opinion> opinions = opinionMapper.selectList(new QueryWrapper<>());
        List<OpinionTree<Opinion>> trees = this.convertOpinions(opinions);
        return TreeUtil.buildOpinionTree(trees);
    }

    @Override
    public List<OpinionTree<Opinion>> findOpinions(Opinion opinion) {
        QueryWrapper<Opinion> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(opinion.getName())) {
            queryWrapper.lambda().eq(Opinion::getName, opinion.getName());
        }
        List<Opinion> opinions = this.opinionMapper.selectList(queryWrapper);
        List<OpinionTree<Opinion>> trees = this.convertOpinions(opinions);
        return TreeUtil.buildOpinionTree(trees);
    }


    @Override
    @Transactional
    public void createOpinion(Opinion opinion) {
        Integer parentId = opinion.getParentId();
        if (parentId == null) {
            opinion.setParentId(0);
        }
        this.save(opinion);
    }


    @Override
    @Transactional
    public void updateOpinion(Opinion opinion) {
        opinionMapper.updateById(opinion);
    }


    @Override
    @Transactional
    public void deleteOpinions(String[] opinionIds) {
        if(opinionIds.length>0){
            for (String opinionId : opinionIds) {
                opinionMapper.deleteOpinions(opinionId);
            }
        }
    }

    @Override
    public List<Opinion> getOpinions(String name) {
        List<Opinion> list = opinionMapper.getOpinions(name);
        return list;
    }


    private List<OpinionTree<Opinion>> convertOpinions(List<Opinion> opinions) {
        List<OpinionTree<Opinion>> trees = new ArrayList<>();
        opinions.forEach(opinion -> {
            OpinionTree<Opinion> tree = new OpinionTree<>();
            tree.setId(String.valueOf(opinion.getId()));
            tree.setParentId(String.valueOf(opinion.getParentId()));
            tree.setName(opinion.getName());
            tree.setData(opinion);
            trees.add(tree);
        });
        return trees;
    }
}
