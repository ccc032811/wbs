package com.neefull.fsp.web.qff.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neefull.fsp.web.common.entity.QueryRequest;
import com.neefull.fsp.web.qff.entity.Query;
import com.neefull.fsp.web.qff.entity.Recent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**近效期QFF
 * @Author: chengchengchu
 * @Date: 2019/11/29  11:33
 */
@Component
public interface RecentMapper extends BaseMapper<Recent> {


    /**查询近效期QFF
     * @param page
     * @param query
     * @return
     */
    IPage<Recent> getRecentPage(Page page, Query query);

    /**更新近效期QFF状态
     * @param id
     * @param status
     * @return
     */
    Integer updateRecentStatus(@Param("id") Integer id, @Param("status") Integer status);
}
