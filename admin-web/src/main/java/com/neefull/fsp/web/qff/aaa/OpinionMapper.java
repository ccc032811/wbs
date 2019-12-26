/*
package com.neefull.fsp.web.qff.aaa;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neefull.fsp.web.qff.entity.Opinion;
import com.neefull.fsp.web.qff.entity.Query;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

*/
/**审批意见
 * @Author: chengchengchu
 * @Date: 2019/11/27  15:59
 *//*

@Mapper
public interface OpinionMapper extends BaseMapper<Opinion> {

    */
/**获取审批意见
     * @param name
     * @return
     *//*

    List<Opinion> getOpinion(@Param("name") String name);

    */
/**查询所有
     * @param page
     * @param query
     * @return
     *//*

    IPage<Opinion> queryAll(Page page, Query query);
}
*/
