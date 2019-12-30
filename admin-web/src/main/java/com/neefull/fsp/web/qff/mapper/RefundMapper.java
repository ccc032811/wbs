package com.neefull.fsp.web.qff.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neefull.fsp.web.common.entity.QueryRequest;
import com.neefull.fsp.web.qff.entity.Query;
import com.neefull.fsp.web.qff.entity.Refund;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**退货QFF
 * @Author: chengchengchu
 * @Date: 2019/11/29  13:54
 */
@Component
public interface RefundMapper extends BaseMapper<Refund> {


    /**查询退货QFF
     * @param page
     * @param query
     * @return
     */
    IPage<Refund> getRefundPage(Page<Refund> page,  Query query);

    /**更新退货QFF状态
     * @param number
     * @param status
     * @return
     */
    Integer updateRefundStatus(@Param("id") Integer id, @Param("status") Integer status);
}
