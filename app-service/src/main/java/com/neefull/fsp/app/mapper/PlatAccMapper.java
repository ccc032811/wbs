package com.neefull.fsp.app.mapper;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neefull.fsp.app.entity.PlatAccount;
import org.apache.ibatis.annotations.Select;

/**
 * @author pei.wang
 */
public interface PlatAccMapper extends BaseMapper<PlatAccount> {

    /**
     * 获取当前权重最高的账户
     *
     * @return
     */
    @DS("slave")
    @Select("SELECT ac_name,ac_no,bank_name,sub_branch FROM t_plat_account ORDER BY weight DESC LIMIT 1")
    public PlatAccount getDefaultPlatAccount();

}
