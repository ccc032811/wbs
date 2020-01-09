package com.neefull.fsp.web.qff.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.web.qff.config.ProcessInstanceProperties;
import com.neefull.fsp.web.qff.entity.*;
import com.neefull.fsp.web.qff.mapper.RecentExcelImportMapper;
import com.neefull.fsp.web.qff.mapper.RecentMapper;
import com.neefull.fsp.web.qff.service.IDateImageService;
import com.neefull.fsp.web.qff.service.IRecentService;
import com.neefull.fsp.web.qff.utils.ProcessConstant;
import com.neefull.fsp.web.system.entity.User;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public IPage<Recent> getRecentPage(Query query) {
        Page<Recent> page = new Page<>(query.getPageNum(),query.getPageSize());
        IPage<Recent> pageInfo = recentMapper.getRecentPage(page,query);
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
    public IPage<RecentExcelImport> getRecentExcelImportPage(Query query) {
        Page<RecentExcelImport> page = new Page<>(query.getPageNum(),query.getPageSize());
        IPage<RecentExcelImport> pageInfo = recentExcelImportMapper.getRecentExcelImportPage(page,query);
        return pageInfo;
    }

}
