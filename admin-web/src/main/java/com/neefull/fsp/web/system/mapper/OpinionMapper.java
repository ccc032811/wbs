package com.neefull.fsp.web.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neefull.fsp.web.system.entity.Opinion;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface OpinionMapper extends BaseMapper<Opinion> {
    /**
     * 递归删除部门
     *
     * @param
     */
    void deleteOpinions(@Param("opinionId") String opinionId);

    /**查询
     * @param name
     * @return
     */
    List<Opinion> getOpinions(@Param("name") String name);


    String queryOpionById(@Param("boxtype") String boxtype);

}
