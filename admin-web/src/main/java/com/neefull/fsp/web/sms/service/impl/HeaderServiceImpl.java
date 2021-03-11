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
import com.neefull.fsp.web.sms.service.IScanLogService;
import com.neefull.fsp.web.sms.utils.ScanComment;
import com.neefull.fsp.web.sms.utils.XmlUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
    @Autowired@Lazy
    private IScanLogService scanLogService;


    @Override
    @Transactional
    public void insertHeader(Header header) {
        this.baseMapper.insert(header);
    }


    @Override
    public IPage<Header> queryHeaderList(Header header) {
//        Header singleHeader = (Header) ScanComment.containPlant(header);

        //分页查询
        IPage<Header> headerPage = new Page<>(header.getPageNum(),header.getPageSize());
        return this.baseMapper.getPageHeader(headerPage,header);
    }


    @Override
    public IPage<Header> queryCompareList(Header header) {
//        Header singleHeader = (Header) ScanComment.containPlant(header);
        IPage<Header> headerPage = new Page<>(header.getPageNum(),header.getPageSize());
        return this.baseMapper.queryCompareList(headerPage,header);
    }


    @Override
    @Transactional
    public void updateStatus(String delivery, String status) {
        this.baseMapper.updateDeliveryStatus(delivery,status);
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
    public void updateErrorMsg(String delivery, String message) {
        this.baseMapper.updateError(message,delivery);
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
    public HeaderVo queryScanDn(String delivery) {
        HeaderVo headerVo = this.baseMapper.queryScanDn(delivery);
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
            if(head.getStatus().equals(ScanComment.STATUS_ZERO)){
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

    @Override
    @Transactional
    public void updateUserByDelivery(String dn, String userName, String format) {
        this.baseMapper.updateUserByDelivery(dn,userName,format);
    }


    @Override
    @Transactional
    public void insertHeaderAndDetail(String message,Integer id) {

        Header header = XmlUtils.resolverSapMessage(message);
        if(header!=null){
            insertHeader(header);
        }
        List<Detail> detailList = header.getDetailList();
        if(CollectionUtils.isNotEmpty(detailList)){
            for (Detail detail : detailList) {
                detailService.insertDetail(detail);
            }
        }
        scanLogService.updateStatus(id,ScanComment.STATUS_TWO);
    }



    @Override
    @Transactional
    public void updateDeliveryStatus(String dn, String status) {
        this.baseMapper.updateDeliveryStatus(dn,status);
    }

    @Override
    public List<Header> getHeaderExcel(Header header) {
        return this.baseMapper.getHeaderExcel(header);
    }

    @Override
    public List<Header> getCompareExcel(Header header) {

        return this.baseMapper.getCompareExcel(header);
    }

    @Override
    @Transactional
    public void deleteByDelivery(String delivery) {
        QueryWrapper<Header> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("delivery",delivery);
        this.baseMapper.delete(queryWrapper);
    }


}
