package com.neefull.fsp.web.qff.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.web.common.entity.QueryRequest;
import com.neefull.fsp.web.qff.entity.ProcessHistory;
import com.neefull.fsp.web.qff.entity.Query;
import com.neefull.fsp.web.qff.entity.Refund;
import com.neefull.fsp.web.system.entity.User;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

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
     * @param query
     * @return
     */
    IPage<Refund> getRefundPage( Query query);

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
