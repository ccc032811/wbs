package com.neefull.fsp.web.sms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.web.sms.entity.ScanLog;
import com.neefull.fsp.web.sms.entity.vo.DetailScanVo;
import com.neefull.fsp.web.sms.entity.vo.DetailVo;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * @Author: chengchengchu
 * @Date: 2020/11/26  14:32
 */
public interface IScanLogService  extends IService<ScanLog> {


    /**根据DN号查询数据库中对应的信息
     * @param delivery
     * @return
     */
    ScanLog queryDnByDelivery(String delivery);

    /**查询未解析的DN记录
     * @return
     */
//    List<ScanLog> selectScanLogInsert();

    /**更新DN记录状态
     * @param headerList
     */
//    void updateScanLogStatus(List<ScanLog> headerList);

    /**查询DN获取记录集合
     * @param scanLog
     * @return
     */
    IPage<ScanLog> queryScanLogList(ScanLog scanLog);

    /**根据DN号去soap查询对应的信息
     * @param delivery
     * @return
     */
    DetailScanVo getDnMessageByDelivery(String delivery);


    /**根据id更新
     * @param id
     * @param status
     */
    void updateStatus(Integer id, String status);


    List<ScanLog> selectScanLogInsert();


    void updateScanLogStatus(List<ScanLog> headerList);

    @Async("scanAsyncThreadPool")
    void insertScanLog(String message, String delivery);
}
