package com.neefull.fsp.web.qff.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neefull.fsp.web.qff.entity.Commodity;
import com.neefull.fsp.web.qff.entity.Query;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @Author: chengchengchu
 * @Date: 2019/12/6  18:55
 */
@Component
public interface CommodityMapper extends BaseMapper<Commodity> {

    /**对养护QFF分页查询
     * @param page
     * @param query
     * @return
     */
    IPage<Commodity> getConservePage(Page  page, @Param("query") Query query);

    /**更新养护QFF状态
     * @param id
     * @param status
     * @return
     */
    Integer updateConserveStatus(@Param("id") Integer id , @Param("status") Integer status);
}
