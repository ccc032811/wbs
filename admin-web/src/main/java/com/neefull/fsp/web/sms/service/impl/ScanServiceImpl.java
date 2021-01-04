package com.neefull.fsp.web.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.web.sms.entity.*;
import com.neefull.fsp.web.sms.entity.vo.HeaderVo;
import com.neefull.fsp.web.sms.mapper.ScanMapper;
import com.neefull.fsp.web.sms.service.IDetailService;
import com.neefull.fsp.web.sms.service.IHeaderService;
import com.neefull.fsp.web.sms.service.IScanLogService;
import com.neefull.fsp.web.sms.service.IScanService;
import com.neefull.fsp.web.sms.utils.ScanComment;
import com.neefull.fsp.web.sms.utils.XmlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author: chengchengchu
 * @Date: 2020/11/24  14:38
 */

@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ScanServiceImpl extends ServiceImpl<ScanMapper, Scan> implements IScanService {

    @Autowired
    private IHeaderService headerService;
    @Autowired
    private IDetailService detailService;


    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public void insertScanMsg(ScanAdd scanAdd) {
        for (Scan scan : scanAdd.getScanList()) {
            if(!scan.getFlag().equals(ScanComment.STATUS_THREE)){
                this.baseMapper.insert(scan);
            }
        }
        headerService.updateStatus(scanAdd.getDelivery(), ScanComment.STATUS_ONE);
    }


    @Override
    public IPage<Scan> getScanInfoList(Scan scan) {
        Scan singleScan = (Scan) ScanComment.containPlant(scan);
        IPage<Scan> scanPage = new Page<>(singleScan.getPageNum(),singleScan.getPageSize());
        return this.baseMapper.getScanInfoList(scanPage,singleScan);

    }


    @Override
    public IPage<Scan> queryScanDetailList(Scan scan) {
        Scan singleScan = (Scan) ScanComment.containPlant(scan);
        IPage<Scan> scanPage = new Page<>(singleScan.getPageNum(),singleScan.getPageSize());
        return this.baseMapper.queryScanDetailList(scanPage,singleScan);
    }


    @Override
    public List<Scan> queryScanByDelivery(String delivery) {
        QueryWrapper<Scan> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("delivery",delivery);
        queryWrapper.eq("del",ScanComment.STATUS_ONE);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public void updateScanStatus(Integer id, String status) {
        this.baseMapper.updateScanStatus(id,status);
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public void editScanDetail(List<Scan> scanList) {
        for (Scan scan : scanList) {

            QueryWrapper<Scan> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("delivery",scan.getDelivery());
            queryWrapper.eq("mat_code",scan.getMatCode());
            queryWrapper.eq("batch",scan.getBatch());

            if(scan.getFlag().equals(ScanComment.STATUS_ONE)){
                this.baseMapper.insert(scan);
            }else if(scan.getFlag().equals(ScanComment.STATUS_TWO)){
                this.baseMapper.update(scan,queryWrapper);
            }else if(scan.getFlag().equals(ScanComment.STATUS_THREE)){
                Scan sca = new Scan();
                sca.setDel(ScanComment.STATUS_TWO);
                this.baseMapper.update(sca,queryWrapper);
            }

//            this.baseMapper.update(scan,queryWrapper);
//            Scan isScan = this.baseMapper.selectOne(queryWrapper);
//
//            if(isScan==null){
//                this.baseMapper.insert(scan);
//            }else {
//                scan.setId(isScan.getId());
//                this.baseMapper.updateById(scan);
//            }
            headerService.updateStatus(scan.getDelivery(),ScanComment.STATUS_ONE);
        }
    }


    @Override
    public Scan getScanDetail(String delivery, String matCode, String batch) {
        QueryWrapper<Scan> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("delivery",delivery);
        queryWrapper.eq("mat_code",matCode);
        queryWrapper.eq("batch",batch);
        queryWrapper.eq("del",ScanComment.STATUS_ONE);
        return this.baseMapper.selectOne(queryWrapper);
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public void deleteScanDetail(String delivery) {
        Scan scan = new Scan();
        scan.setDel(ScanComment.STATUS_TWO);
        QueryWrapper<Scan> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("delivery",delivery);
        this.baseMapper.update(scan,queryWrapper);
        headerService.updateStatus(delivery,ScanComment.STATUS_ZERO);
        detailService.updateStatusByDelivery(delivery,ScanComment.STATUS_ZERO);

    }

    @Override
    public List<Scan> queryScanAndCountByDelivery(String delivery) {
        return this.baseMapper.queryScanAndCountByDelivery(delivery);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public void deleteScanById(Integer id,String delivery) {
        this.baseMapper.deleteScanById(id,ScanComment.STATUS_TWO);
        headerService.updateStatus(delivery,ScanComment.STATUS_ONE);
    }


    @Override
    public Scan getScanDetailById(String id) {
        return this.baseMapper.selectById(id);
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public List<HeaderVo> auditDn(String dns,String userName) {

        String[] deliveryList = dns.split(StringPool.COMMA);
        List<HeaderVo> headerVos = new ArrayList<>();

        for (String dn : deliveryList) {

            StringBuffer errorMsg = new StringBuffer();

            Header header = headerService.queryHeaderDetailByDelivery(dn);
            List<Header> headerList = headerService.selectHeaderByScanDn(dn);

            boolean compare = false;
            if(headerList.size()>1){
                for (Header head : headerList) {
                    if(!header.getPlant().equals(head.getPlant())
                            ||!header.getSoldParty().equals(head.getSoldParty())
                            ||!header.getShipParty().equals(head.getShipParty())){
                        compare = true;
                    }
                }
            }
            if(compare){
                StringBuffer buffer = new StringBuffer("拼箱不符合规则,所属ＤＮ号分别为: ");
                for (int i = 0;i<headerList.size();i++){
                    if(i==headerList.size()-1){
                        buffer.append(headerList.get(i).getDelivery()).append("; ");
                    }else {
                        buffer.append(headerList.get(i).getDelivery()).append("和");
                    }
                }
                headerService.updateErrorMsg(header.getId(),buffer.toString());
                errorMsg.append(buffer.toString());
            }else {
                if(StringUtils.isNotEmpty(header.getErrorMessage())){
                    headerService.updateErrorMsg(header.getId(),null);
                }
            }
            List<Detail> detailList = header.getDetailList();
            List<Detail> scanList = detailService.selectScanDetail(dn);

            List<Detail> defultList = new ArrayList<>();
            for (Detail detail : detailList) {
                if(scanList.contains(detail)){
                    if(StringUtils.isNotEmpty(detail.getErrorMessage())){
                        detailService.updateErrorMsg(detail.getId(),null,null);
                    }
                }else {
                    defultList.add(detail);
                }
            }

            if(CollectionUtils.isNotEmpty(defultList)){
                for (Detail detail : defultList) {
                    Detail maDeta = new Detail();
                    boolean material = true;
                    for (Detail deta : scanList) {
                        if(detail.getMaterial().equals(deta.getMaterial())&&detail.getRocheBatch().equals(deta.getRocheBatch())){
                            maDeta = deta;
                            material = false;
                            break;
                        }
                    }
                    if(material){
                        errorMsg.append("该物料:" + detail.getMaterial() + "不存在或该批次:" + detail.getRocheBatch() + "不存在; ");
                        detailService.updateErrorMsg(detail.getId(),"该物料:" + detail.getMaterial() + "不存在或该批次:" + detail.getRocheBatch() + "不存在; ",ScanComment.STATUS_ONE);
                    }
                    if(!material){
                        if(!detail.getQuantity().equals(maDeta.getQuantity())){
                            errorMsg.append("该物料:"+detail.getMaterial()+"和批次:" + detail.getRocheBatch() +"的数量不相同; ");
                            detailService.updateErrorMsg(detail.getId(),"该物料:"+detail.getMaterial()+"和批次:" + detail.getRocheBatch() +"的数量不相同; ",ScanComment.STATUS_ONE);
                        }
                    }
                }
            }

            for (Detail detail : detailList) {
                for (Detail scanDatail : scanList) {
                    if(detail.getRocheBatch().equals(scanDatail.getRocheBatch())&&detail.getMaterial().equals(scanDatail.getMaterial())){
                        detailService.updateScanQuntity(detail.getId(),scanDatail.getQuantity());
                    }
                }
            }
            HeaderVo headerVo = new HeaderVo();
            headerVo.setDelivery(dn);
            if(StringUtils.isEmpty(errorMsg)){
                headerService.updateStatus(dn,ScanComment.STATUS_THREE);
                headerVo.setErrorMessage(ScanComment.ERRORMSG);
            }else {
                headerService.updateStatus(dn,ScanComment.STATUS_TWO);
                headerVo.setErrorMessage(errorMsg.toString());

            }
            headerVos.add(headerVo);
            headerService.updateUserByDelivery(dn,userName, DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));

        }
        return headerVos;
    }




}
