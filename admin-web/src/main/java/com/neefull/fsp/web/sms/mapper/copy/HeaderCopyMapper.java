package com.neefull.fsp.web.sms.mapper.copy;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neefull.fsp.web.sms.entity.copy.HeaderCopy;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @Author: chengchengchu
 * @Date: 2021/1/12  18:07
 */
@Component
@Mapper
public interface HeaderCopyMapper extends BaseMapper<HeaderCopy> {

    List<HeaderCopy> selectCopyDateByTime(Date time);

}
