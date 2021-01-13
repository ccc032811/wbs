package com.neefull.fsp.web.sms.mapper.copy;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neefull.fsp.web.sms.entity.copy.ScanCopy;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: chengchengchu
 * @Date: 2021/1/13  9:40
 */
@Component
@Mapper
public interface ScanCopyMapper extends BaseMapper<ScanCopy> {

    List<ScanCopy> selectCopyScanByDelivery(String delivery);

}
