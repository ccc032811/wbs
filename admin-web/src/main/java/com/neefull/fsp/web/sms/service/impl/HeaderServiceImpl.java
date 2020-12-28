package com.neefull.fsp.web.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.web.sms.entity.Detail;
import com.neefull.fsp.web.sms.entity.Header;
import com.neefull.fsp.web.sms.entity.vo.HeaderVo;
import com.neefull.fsp.web.sms.mapper.HeaderMapper;
import com.neefull.fsp.web.sms.service.IDetailService;
import com.neefull.fsp.web.sms.service.IHeaderService;
import com.neefull.fsp.web.sms.utils.ScanComment;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: chengchengchu
 * @Date: 2020/11/24  14:17
 */

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class HeaderServiceImpl extends ServiceImpl<HeaderMapper, Header> implements IHeaderService {

    @Autowired
    private IDetailService detailService;


    @Override
    @Transactional
    public void insertHeader(Header header) {
        this.baseMapper.insert(header);
    }


    @Override
    public IPage<Header> queryHeaderList(Header header) {
        Header singleHeader = (Header) ScanComment.containPlant(header);
        IPage<Header> headerPage = new Page<>(singleHeader.getPageNum(),singleHeader.getPageSize());
        return this.baseMapper.getPageHeader(headerPage,singleHeader);
    }

    @Override
    public IPage<Header> queryCompareList(Header header) {
        Header singleHeader = (Header) ScanComment.containPlant(header);
        IPage<Header> headerPage = new Page<>(singleHeader.getPageNum(),singleHeader.getPageSize());
        return this.baseMapper.queryCompareList(headerPage,singleHeader);
    }


    @Override
    @Transactional
    public void updateStatus(String delivery, String status) {
        this.baseMapper.updateStatusByDelivery(delivery,status);
    }


    @Override
    public List<Header> selectHeaderByScanDn(String dn) {
        return this.baseMapper.selectHeaderByScanDn(dn);
    }


    @Override
    public Header queryHeaderDetailByDelivery(String dn) {
        return this.baseMapper.queryHeaderDetailByDelivery(dn);
    }


    @Override
    @Transactional
    public void updateErrorMsg(Integer id, String msg) {
        this.baseMapper.updateErrorMsg(id,msg);
    }


    @Override
    public List<Header> querySubmitDelivery() {
        QueryWrapper<Header> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",ScanComment.STATUS_THREE);
        return this.baseMapper.selectList(queryWrapper);
    }


    @Override
    public Header queryHeaderByDelivery(String delivery) {
        QueryWrapper<Header> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("delivery",delivery);
        return this.baseMapper.selectOne(queryWrapper);

    }


    @Override
    public HeaderVo queryScanDn(String plant,String delivery) {
        HeaderVo headerVo = this.baseMapper.queryScanDn(plant, delivery, ScanComment.STATUS_ONE ,ScanComment.STATUS_TWO);
        if(headerVo!=null){
            List<Detail> detailList = detailService.queryDetailByDelivery(delivery);
            StringBuffer msg = new StringBuffer();
            if(StringUtils.isNotEmpty(headerVo.getErrorMessage())){
                msg.append(headerVo.getErrorMessage());
            }
            for (Detail detail : detailList) {
                if(StringUtils.isNotEmpty(detail.getErrorMessage())){
                    msg.append(detail.getErrorMessage());
                }
            }
            headerVo.setErrorMessage(msg.toString());
        }
        return headerVo;
    }


    @Override
    public Map<String, Integer> queryScanNumber(Header header) {
        List<Header> headerList = this.baseMapper.selecHeaderList(header);
        int zeroStatus ,oneStatus ,twoStatus ,threeStatus ,fourStatus;
        zeroStatus = oneStatus = twoStatus = threeStatus = fourStatus = 0;

        for (Header head : headerList) {
            if(StringUtils.isEmpty(head.getStatus())){
                zeroStatus += 1;
            }else if(head.getStatus().equals(ScanComment.STATUS_ONE)){
                oneStatus += 1;
            }else if(head.getStatus().equals(ScanComment.STATUS_TWO)){
                twoStatus += 1;
            }else if(head.getStatus().equals(ScanComment.STATUS_THREE)){
                threeStatus += 1;
            } else if(head.getStatus().equals(ScanComment.STATUS_FOUR)){
                fourStatus += 1;
            }
        }
        Map<String,Integer> map = new HashMap<>();

        map.put("count",headerList.size());
        map.put("zeroStatus",zeroStatus);
        map.put("oneStatus",oneStatus);
        map.put("twoStatus",twoStatus);
        map.put("threeStatus",threeStatus);
        map.put("fourStatus",fourStatus);

        return map;
    }


}
