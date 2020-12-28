package com.neefull.fsp.web.sms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.web.sms.entity.*;
import com.neefull.fsp.web.sms.entity.vo.HeaderVo;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * @Author: chengchengchu
 * @Date: 2020/11/24  14:37
 */

public interface IScanService  extends IService<Scan> {


    /**新增扫描记录信息
     * @param scanList
     */
    void insertScanMsg(ScanAdd scanAdd);


    /**查询扫描信息  分页
     * @param scan
     * @return
     */
    IPage<Scan> getScanInfoList(Scan scan);


    /**查询扫描记录信息
     * @param scan
     * @return
     */
    IPage<Scan> queryScanDetailList(Scan scan);

    /**根据id查询扫描记录
     * @param id
     * @return
     */
    Scan getScanDetailById(String id);

    /**审核DN
     * @param dn
     * @return
     */
    List<HeaderVo> auditDn(String dns);


    /**根据DN查询
     * @param delivery
     * @return
     */
    List<Scan> queryScanByDelivery(String delivery);


    /**更新扫描表状态
     * @param id
     * @param status
     */
    void updateScanStatus(Integer id, String status);

    /**修改扫描信息
     * @param scan
     */
    void editScanDetail(List<Scan> scanList);


    /**根据dn，物料，批次查询
     * @param delivery
     * @param matCode
     * @param batch
     * @return
     */
    Scan getScanDetail(String delivery, String matCode, String batch);


    /**异步解析入库
     * @param message
     * @param id
     */
    @Async("scanAsyncThreadPool")
    void insertHeaderAndDetail(String message,Integer id);


    /**删除已扫信息
     * @param delivery
     */
    void deleteScanDetail(String delivery);

}
