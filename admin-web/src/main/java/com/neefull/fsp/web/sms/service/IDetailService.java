package com.neefull.fsp.web.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.web.sms.entity.Detail;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author: chengchengchu
 * @Date: 2020/11/24  14:23
 */
public interface IDetailService extends IService<Detail> {

    /**新增DN Detail
     * @param detail
     */
    void insertDetail(Detail detail);

    /**根据DN号查询对应的DN Detail集合
     * @param detail
     * @return
     */
    Map<String, Object> getDetailList(Detail detail);

    /**根据id查询DN Detail
     * @param id
     * @return
     */
    Detail getDetailById(Integer id);

    /**根据DN号查询物料，批次，数量
     * @param dn
     * @return
     */
    List<Detail> selectScanDetail(String dn);

    /**更新已扫数量
     * @param id
     * @param quantity
     */
    void updateScanQuntity(Integer id, String quantity);

    /**更新错误信息
     * @param id
     * @param msg
     */
    void updateErrorMsg(Integer id, String msg,String status);

    /**根据DN查询
     * @param delivery
     * @return
     */
    List<Detail> queryDetailByDelivery(String delivery);

    void updateStatusByDelivery(String delivery, String status);

}
