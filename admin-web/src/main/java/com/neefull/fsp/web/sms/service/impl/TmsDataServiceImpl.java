package com.neefull.fsp.web.sms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.web.sms.entity.TmsData;
import com.neefull.fsp.web.sms.mapper.TmsDataMapper;
import com.neefull.fsp.web.sms.service.ITmsDataService;
import com.neefull.fsp.web.sms.utils.ScanComment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: chengchengchu
 * @Date: 2020/12/9  11:45
 */


@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TmsDataServiceImpl extends ServiceImpl<TmsDataMapper, TmsData> implements ITmsDataService {


    @Override
    @Transactional
    public void addTmsData(TmsData tmsData) {
        this.baseMapper.insert(tmsData);
    }

    @Override
    public IPage<TmsData> queryTmsList(TmsData tmsData) {
//        TmsData tms = (TmsData) ScanComment.containPlant(tmsData);
        IPage<TmsData> dataPage = new Page<>(tmsData.getPageNum(),tmsData.getPageSize());
        return this.baseMapper.queryTmsPage(dataPage,tmsData);
    }
}
