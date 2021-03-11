package com.neefull.fsp.web.sms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.web.sms.entity.Header;
import com.neefull.fsp.web.sms.entity.vo.HeaderVo;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: chengchengchu
 * @Date: 2020/11/24  14:17
 */
public interface IHeaderService extends IService<Header> {


    /**新增DN Header
     * @param header
     */
    void insertHeader(Header header);

    /**查询DN Header
     * @param header
     * @return
     */
    IPage<Header> queryHeaderList(Header header);

    /**查询扫描对比
     * @param header
     * @return
     */
    IPage<Header> queryCompareList(Header header);

    /**根据DN号更新状态
     * @param delivery
     * @param status
     */
    void updateStatus(String delivery, String status);


    /**根据扫描的DN 查询saop接口DN信息
     * @param dn
     * @return
     */
    List<Header> selectHeaderByScanDn(String dn);

    /**根据DN号查询
     * @param dn
     * @return
     */
    Header queryHeaderDetailByDelivery(String dn);

    /**更新错误信息
     * @param id
     * @param toString
     */
    void updateErrorMsg(String delivery, String msg);

    /**查询可同步的数据
     * @return
     */
    List<Header> querySubmitDelivery();


    /**根据DN查询
     * @param delivery
     * @return
     */
    Header queryHeaderByDelivery(String delivery);

    /**查询已扫但是没审核和审核未通过的dn
     * @param plant
     * @return
     */
    HeaderVo queryScanDn(String delivery);

    /**查询三个状态的数量
     * @return
     */
    Map<String, Integer> queryScanNumber(Header header);


    /**更新审批用户
     * @param dn
     * @param userName
     * @param format
     */
    void updateUserByDelivery(String dn, String userName, String format);

    /**异步解析入库
     * @param message
     * @param id
     */
//    @Async("scanAsyncThreadPool")
    void insertHeaderAndDetail(String message, Integer id);

    void updateDeliveryStatus(String dn, String status);

    List<Header> getHeaderExcel(Header header);

    List<Header> getCompareExcel(Header header);

    void deleteByDelivery(String delivery);



}
