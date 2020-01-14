package com.neefull.fsp.web.qff.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.web.qff.entity.Refund;


/**退货QFF
 * @Author: chengchengchu
 * @Date: 2019/11/29  13:53
 */


public interface IRefundService extends IService<Refund> {


    /**新增退货QFF
     * @param refund
     * @return
     */
    Integer addRefund(Refund refund);

    /**更新退货QFF
     * @param refund
     * @return
     */
    Integer editRefund(Refund refund);

    /**查询退货QFF
     * @param refund
     * @return
     */
    IPage<Refund> getRefundPage(Refund refund);

    /**删除退货QFF
     * @param
     * @return
     */
    Integer updateRefundStatus(Integer id,Integer status);

    /**查询退货QFF
     * @param
     * @return
     */
    Refund queryRefundById(Integer id);

}
