package com.neefull.fsp.app.mapper;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neefull.fsp.app.entity.DictBanks;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author pei.wang
 */
@DS("slave")
public interface DictBanksMapper extends BaseMapper<DictBanks> {

    @Select("select distinct bank_id,bank_name from t_dict_banks")
    List<DictBanks> listAllBank();

    @Select("select  bank_id,bank_name from t_dict_banks where bank_abbr=#{bankAbbr} limit 1")
    DictBanks findBankName(@Param("bankAbbr") String bankAbbr);

    @Select("select distinct province_id,province from t_dict_banks order by province_id")
    List<DictBanks> queryAllProvince();

    @Select("select distinct city_id,city from t_dict_banks where province_id = #{provinceId} order by city_id")
    List<DictBanks> queryAllCity(@Param("provinceId") long provinceId);

    @Select("select  sub_branch_id,sub_branch_name from t_dict_banks where city_id = #{cityId} and bank_id=#{bankId} order by sub_branch_id")
    List<DictBanks> querySubBranch(@Param("cityId") long cityId, @Param("bankId") long bankId);


}
