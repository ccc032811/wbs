package com.neefull.fsp.web.sms.mapper.copy;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neefull.fsp.web.sms.entity.copy.DetailCopy;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: chengchengchu
 * @Date: 2021/1/13  9:38
 */
@Component
@Mapper
public interface DetailCopyMapper extends BaseMapper<DetailCopy> {

    List<DetailCopy> selectCopyDetailByDelivery(String delivery);

}
