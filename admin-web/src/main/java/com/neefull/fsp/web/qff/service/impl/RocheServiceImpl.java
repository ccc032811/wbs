package com.neefull.fsp.web.qff.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.web.common.entity.QueryRequest;
import com.neefull.fsp.web.qff.config.ProcessInstanceProperties;
import com.neefull.fsp.web.qff.entity.*;
import com.neefull.fsp.web.qff.mapper.RocheMapper;
import com.neefull.fsp.web.qff.service.IDateImageService;
import com.neefull.fsp.web.qff.service.IRocheService;
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

/**罗氏内部发起QFF
 * @Author: chengchengchu
 * @Date: 2019/11/29  13:13
 */

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RocheServiceImpl extends ServiceImpl<RocheMapper, Roche> implements IRocheService {


    @Autowired
    private RocheMapper rocheMapper;

    @Override
    public Integer addRoche(Roche roche) {
        int count = rocheMapper.insert(roche);
        return count;
    }

    @Override
    public Integer editRoche(Roche roche) {
        UpdateWrapper<Roche> update = new UpdateWrapper<>();
        update.eq("number",roche.getNumber());
        int count = rocheMapper.update(roche, update);
        return count;
    }

    @Override
    public IPage<Roche> getRochePage(Query query) {
        Page<Roche> page = new Page<>(query.getPageNum(),query.getPageSize());
        IPage<Roche> pageInfo = rocheMapper.getRochePage(page,query);
        return pageInfo;
    }

    @Override
    public Integer updateRocheStatus(Integer id,Integer status) {
        Integer count = rocheMapper.updateRocheStatus(id,status);
        return count;
    }

    @Override
    public Roche queryRocheById(Integer id) {
        Roche roche = rocheMapper.selectById(id);
        return roche;
    }

}
