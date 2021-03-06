package com.neefull.fsp.web.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.neefull.fsp.web.sms.entity.Header;
import com.neefull.fsp.web.sms.entity.vo.HeaderVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @Author: chengchengchu
 * @Date: 2020/11/24  14:19
 */
@Mapper
public interface HeaderMapper extends BaseMapper<Header> {

    IPage<Header> getPageHeader(IPage<Header> headerPage, Header header);

    IPage<Header> queryCompareList(IPage<Header> headerPage, Header singleHeader);

    void updateStatusByDelivery(@Param("delivery") String delivery, @Param("status") String status);

    List<Header> selectHeaderByScanDn(@Param("dn") String dn);

    Header queryHeaderDetailByDelivery(@Param("dn") String dn);

    void updateError( @Param("message") String message,@Param("delivery") String delivery);

    HeaderVo queryScanDn( @Param("delivery") String delivery);

    List<Header> selecHeaderList(Header header);

    void updateUserByDelivery(@Param("dn") String dn, @Param("userName") String userName, @Param("format") String format);

    void updateDeliveryStatus(@Param("dn") String dn, @Param("status") String status);

    List<Header> getHeaderExcel(Header header);

    List<Header> getCompareExcel(Header header);




}
