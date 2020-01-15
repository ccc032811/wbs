package com.neefull.fsp.web.qff.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.web.qff.entity.*;
import com.neefull.fsp.web.qff.mapper.RecentExcelImportMapper;
import com.neefull.fsp.web.qff.mapper.RecentMapper;
import com.neefull.fsp.web.qff.service.IRecentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



/**近效期QFF
 * @Author: chengchengchu
 * @Date: 2019/11/29  11:32
 */

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RecentServiceImpl extends ServiceImpl<RecentMapper, Recent> implements IRecentService {


    @Autowired
    private RecentMapper recentMapper;
    @Autowired
    private RecentExcelImportMapper recentExcelImportMapper;

    @Override
    public Integer addRecent(Recent recent) {
        int count = recentMapper.insert(recent);
        return count;
    }

    @Override
    public Integer editRecent(Recent recent) {
        int count = recentMapper.updateById(recent);
        return count;
    }

    @Override
    public IPage<Recent> getRecentPage(Recent recent) {
        Page<Recent> page = new Page<>(recent.getPageNum(),recent.getPageSize());
        IPage<Recent> pageInfo = recentMapper.getRecentPage(page,recent);
        return pageInfo;
    }

    @Override
    public Integer updateRecentStatus(Integer id,Integer status) {
        Integer count = recentMapper.updateRecentStatus(id,status);
        return count;
    }

    @Override
    public Recent queryRecentById(Integer id) {
        Recent recent = recentMapper.selectById(id);
        return recent;
    }

    @Override
    public IPage<RecentExcelImport> getRecentExcelImportPage(Recent recent) {
        Page<RecentExcelImport> page = new Page<>(recent.getPageNum(),recent.getPageSize());
        IPage<RecentExcelImport> pageInfo = recentExcelImportMapper.getRecentExcelImportPage(page,recent);
        return pageInfo;
    }

}