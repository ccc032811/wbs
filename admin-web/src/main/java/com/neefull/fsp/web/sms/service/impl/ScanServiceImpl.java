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

import java.math.BigDecimal;
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
    @Transactional
    public void insertScanMsg(ScanAdd scanAdd) {

        //判断不为删除的状态的数据直接入库
        for (Scan scan : scanAdd.getScanList()) {
            if(!scan.getFlag().equals(ScanComment.STATUS_THREE)){
                this.baseMapper.insert(scan);
            }
        }
        //更新detail的已扫数量
        List<Detail> detailList = addScanQuantity(scanAdd.getDelivery(), scanAdd.getScanList());
        for (Detail detail : detailList) {
            detailService.updateScanQuntity(detail.getId(),detail.getScanQuantity());
        }
        //状态更新
        headerService.updateDeliveryStatus(scanAdd.getDelivery(), ScanComment.STATUS_ONE);
    }


    private List<Detail> addScanQuantity(String delivery,List<Scan> scanList){
        List<Detail> detailList = detailService.queryDetailByDelivery(delivery);
        //对相同物料的数量进行相加
        for (Detail detail : detailList) {
            String material = detail.getMaterial();
            BigDecimal matDec = new BigDecimal("0");
            for (Scan scan : scanList) {
                if (scan.getMatCode().equals(material)) {
                    BigDecimal scanDec = new BigDecimal(scan.getQuantity());
                    matDec = matDec.add(scanDec);
                }
            }
            if(!matDec.toString().equals(ScanComment.STATUS_ZERO)){
                detail.setScanQuantity(matDec.toString());
            }
        }
        return detailList;
    }


    @Override
    public IPage<Scan> getScanInfoList(Scan scan) {
//        Scan singleScan = (Scan) ScanComment.containPlant(scan);
        IPage<Scan> scanPage = new Page<>(scan.getPageNum(),scan.getPageSize());
        return this.baseMapper.getScanInfoList(scanPage,scan);

    }


    @Override
    public IPage<Scan> queryScanDetailList(Scan scan) {
//        Scan singleScan = (Scan) ScanComment.containPlant(scan);
        IPage<Scan> scanPage = new Page<>(scan.getPageNum(),scan.getPageSize());
        return this.baseMapper.queryScanDetailList(scanPage,scan);
    }


    @Override
    public List<Scan> queryScanByDelivery(String delivery) {
        QueryWrapper<Scan> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("delivery",delivery);
        queryWrapper.eq("del",ScanComment.STATUS_ONE);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public void updateScanStatus(Integer id, String status) {
        this.baseMapper.updateScanStatus(id,status);
    }


    @Override
    @Transactional
    public void editScanDetail(List<Scan> scanList) {

        for (Scan scan : scanList) {
            QueryWrapper<Scan> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("delivery",scan.getDelivery());
            queryWrapper.eq("mat_code",scan.getMatCode());
            queryWrapper.eq("box_code",scan.getBoxCode());
            queryWrapper.eq("batch",scan.getBatch());


            if(scan.getFlag().equals(ScanComment.STATUS_ONE)){
                //传过来的flag状态为1 直接入库
                this.baseMapper.insert(scan);
            }else if(scan.getFlag().equals(ScanComment.STATUS_TWO)){
                //传过来的flag状态为2 更新入库
                this.baseMapper.update(scan,queryWrapper);
            }else if(scan.getFlag().equals(ScanComment.STATUS_THREE)){
                //传过来的flag状态为3  删除
                Scan sca = new Scan();
                sca.setDel(ScanComment.STATUS_TWO);
                this.baseMapper.update(sca,queryWrapper);
            }
        }
        //更新已扫数量，并将DN状态更改为1
        if(CollectionUtils.isNotEmpty(scanList)){
            String delivery = scanList.get(0).getDelivery();
            List<Detail> detailList = addScanQuantity(delivery, scanList);
            for (Detail detail : detailList) {
                detailService.updateScanQuntity(detail.getId(),detail.getScanQuantity());
            }
            headerService.updateStatus(delivery,ScanComment.STATUS_ONE);
            this.baseMapper.updateStatusByDelivery(delivery,ScanComment.STATUS_ONE);
            detailService.updateStatusByDelivery(delivery,null);
        }
    }


    @Override
    public Scan getScanDetail(String delivery, String matCode) {
        return this.baseMapper.selectScanByDeliveryAndMatCode(delivery,matCode,ScanComment.STATUS_ONE);
    }


    @Override
    @Transactional
    public void deleteScanDetail(String delivery) {
        //删除扫描记录   并将DN状态更改
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
    @Transactional
    public void deleteScanById(Integer id,String delivery) {
        //删除扫描记录并更新DN状态
        this.baseMapper.deleteScanById(id,ScanComment.STATUS_TWO);
        this.baseMapper.updateStatusByDelivery(delivery,ScanComment.STATUS_ONE);
        headerService.updateStatus(delivery,ScanComment.STATUS_ONE);
        detailService.updateStatusByDelivery(delivery,ScanComment.STATUS_ZERO);

    }

    @Override
    public List<Scan> downScanExcel(Scan scan) {
        return this.baseMapper.downScanExcel(scan);
    }

    @Override
    public List<String> queryBoxTypeByDeliveryAndMatCode(String delivery, String material) {
        return this.baseMapper.queryBoxTypeByDeliveryAndMatCode(delivery,material);
    }

    @Override
    @Transactional
    public void deleteByDelivery(String delivery) {
        QueryWrapper<Scan> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("delivery",delivery);
        this.baseMapper.delete(queryWrapper);
    }


    @Override
    public Scan getScanDetailById(String id) {
        return this.baseMapper.selectById(id);
    }


    @Override
    @Transactional
    public List<HeaderVo> auditDn(String dns,String userName) {
        //审核DN
        String[] deliveryList = dns.split(StringPool.COMMA);
        List<HeaderVo> headerVos = new ArrayList<>();

        for (String dn : deliveryList) {

            StringBuffer errorMsg = new StringBuffer();
            //根据DN获取 header  detail  信息
            Header header = headerService.queryHeaderDetailByDelivery(dn);
            //这个判断有没有拼箱操作
            List<Header> headerList = headerService.selectHeaderByScanDn(dn);

            boolean compare = false;
            if(headerList.size()>1){
                for (Header head : headerList) {
                    if(!header.getPlant().equals(head.getPlant())
                            ||!header.getSoldParty().equals(head.getSoldParty())
                            ||!header.getShipParty().equals(head.getShipParty())){
                        compare = true;
                        break;
                    }
                }
            }
            //判断soap获取的物料，数量  和 扫码的 物料，数量是否相同
            if(compare){
                StringBuffer buffer = new StringBuffer("拼箱不符合规则,所属ＤＮ号分别为: ");
                for (int i = 0;i<headerList.size();i++){
                    if(i==headerList.size()-1){
                        buffer.append(headerList.get(i).getDelivery()).append("; ");
                    }else {
                        buffer.append(headerList.get(i).getDelivery()).append("和");
                    }
                }
                headerService.updateErrorMsg(dn,buffer.toString());
                errorMsg.append(buffer.toString());
            }else {
                if(StringUtils.isNotEmpty(header.getErrorMessage())){
                    headerService.updateErrorMsg(dn,null);
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
                    String batch = "";
                    for (Detail deta : scanList) {
                        if(StringUtils.isNotEmpty(deta.getRocheBatch())){
                            batch = deta.getRocheBatch();
                            if(detail.getMaterial().equals(deta.getMaterial())&&detail.getRocheBatch().equals(deta.getRocheBatch())){
                                maDeta = deta;
                                material = false;
                                break;
                            }
                        }else {
                            if(detail.getMaterial().equals(deta.getMaterial())){
                                maDeta = deta;
                                material = false;
                                break;
                            }
                        }

                    }
                    //物料不一致，拼接字符串
                    if(material){
                        if(StringUtils.isNotEmpty(batch)){
                            errorMsg.append("该物料:" + detail.getMaterial() + "未进行扫描或该批次:" + detail.getRocheBatch() + "未进行扫描; ");
                            detailService.updateErrorMsg(detail.getId(),"该物料:" + detail.getMaterial() + "未进行扫描或该批次:" + detail.getRocheBatch() + "未进行扫描; ",ScanComment.STATUS_ONE);
                        }else {
                            errorMsg.append("该物料:" + detail.getMaterial() + "未进行扫描;");
                            detailService.updateErrorMsg(detail.getId(),"该物料:" + detail.getMaterial() + "未进行扫描;",ScanComment.STATUS_ONE);
                        }

                    }
                    //物料一致，判断数量
                    if(!material){
                        if(StringUtils.isNotEmpty(batch)){
                            if(!detail.getQuantity().equals(maDeta.getQuantity())){
                                errorMsg.append("该物料:"+detail.getMaterial()+"和批次:" + detail.getRocheBatch() +"与订单数量不一致");
                                detailService.updateErrorMsg(detail.getId(),"该物料:"+detail.getMaterial()+"和批次:" + detail.getRocheBatch() +"与订单数量不一致",ScanComment.STATUS_ONE);
                            }
                        }else {
                            if(!detail.getQuantity().equals(maDeta.getQuantity())){
                                errorMsg.append("该物料:"+detail.getMaterial()+"与订单数量不一致; ");
                                detailService.updateErrorMsg(detail.getId(),"该物料:"+detail.getMaterial()+"与订单数量不一致; ",ScanComment.STATUS_ONE);
                            }
                        }

                    }
                }
            }

//            for (Detail detail : detailList) {
//                for (Detail scanDatail : scanList) {
//                    if(detail.getRocheBatch().equals(scanDatail.getRocheBatch())&&detail.getMaterial().equals(scanDatail.getMaterial())){
//                        detailService.updateScanQuntity(detail.getId(),scanDatail.getQuantity());
//                    }
//                }
//            }

            //更新detail  已扫数量
            for (Detail detail : detailList) {
                for (Detail scanDatail : scanList) {
                    if(detail.getMaterial().equals(scanDatail.getMaterial())){
                        detailService.updateScanQuntity(detail.getId(),scanDatail.getQuantity());
                    }
                }
            }

            //返回具体信息给扫码枪
            HeaderVo headerVo = new HeaderVo();
            headerVo.setDelivery(dn);
            if(StringUtils.isEmpty(errorMsg)){
                headerService.updateDeliveryStatus(dn,ScanComment.STATUS_THREE);
                headerVo.setErrorMessage(ScanComment.ERRORMSG);
            }else {
                headerService.updateDeliveryStatus(dn,ScanComment.STATUS_TWO);
                headerVo.setErrorMessage(errorMsg.toString());

            }
            headerVos.add(headerVo);
            headerService.updateUserByDelivery(dn,userName, DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));



        }
        return headerVos;
    }




}
