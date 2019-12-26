package com.neefull.fsp.web.qff.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neefull.fsp.web.common.entity.QueryRequest;
import com.neefull.fsp.web.qff.entity.Query;
import com.neefull.fsp.web.qff.entity.Roche;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**罗氏内部发起QFF
 * @Author: chengchengchu
 * @Date: 2019/11/29  13:14
 */

@Mapper
public interface RocheMapper extends BaseMapper<Roche> {


    /**查询罗氏内部QFF
     * @param page
     * @param query
     * @return
     */
    IPage<Roche> getRochePage(Page<Roche> page,  Query query);

    /**删除罗氏内部QFF
     * @param id
     * @param status
     * @return
     */
    Integer updateRocheStatus(@Param("id") Integer id, @Param("status") Integer status);
}
