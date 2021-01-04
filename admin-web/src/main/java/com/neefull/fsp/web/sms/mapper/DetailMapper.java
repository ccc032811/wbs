package com.neefull.fsp.web.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neefull.fsp.web.sms.entity.Detail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: chengchengchu
 * @Date: 2020/11/24  14:24
 */

@Mapper
public interface DetailMapper extends BaseMapper<Detail> {
    List<Detail> getDetailList(Detail detail);

    List<Detail> selectScanDetail(@Param("dn") String dn);

    void updateScanQuntity(@Param("id") Integer id, @Param("quantity") String quantity);

    void updateupdateErrorMsg(@Param("id") Integer id, @Param("msg") String msg, @Param("status") String status);

    void updateStatusByDelivery(@Param("delivery") String delivery, @Param("status") String status);
}
