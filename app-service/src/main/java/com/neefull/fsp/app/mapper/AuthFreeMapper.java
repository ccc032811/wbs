package com.neefull.fsp.app.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neefull.fsp.app.entity.AuthFreelancer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author pei.wang
 */
public interface AuthFreeMapper extends BaseMapper<AuthFreelancer> {


    List<AuthFreelancer> queryTeamUsers(@Param("projectId") long projectId);

}
