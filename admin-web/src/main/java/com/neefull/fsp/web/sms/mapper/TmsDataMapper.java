package com.neefull.fsp.web.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.neefull.fsp.web.sms.entity.TmsData;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @Author: chengchengchu
 * @Date: 2020/12/9  11:44
 */

@Mapper
public interface TmsDataMapper extends BaseMapper<TmsData> {
    IPage<TmsData> queryTmsPage(IPage<TmsData> dataIPage, TmsData tmsData);
}
