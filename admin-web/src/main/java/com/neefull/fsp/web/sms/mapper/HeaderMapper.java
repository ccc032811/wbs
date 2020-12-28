package com.neefull.fsp.web.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.neefull.fsp.web.sms.entity.Header;
import com.neefull.fsp.web.sms.entity.vo.HeaderVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: chengchengchu
 * @Date: 2020/11/24  14:19
 */

@Component
public interface HeaderMapper extends BaseMapper<Header> {

    IPage<Header> getPageHeader(IPage<Header> headerPage, Header header);

    IPage<Header> queryCompareList(IPage<Header> headerPage, Header singleHeader);

    void updateStatusByDelivery(@Param("delivery") String delivery, @Param("status") String status);

    List<Header> selectHeaderByScanDn(@Param("dn") String dn);

    Header queryHeaderDetailByDelivery(@Param("dn") String dn);

    void updateErrorMsg(@Param("id") Integer id, @Param("msg") String msg);

    HeaderVo queryScanDn(@Param("plant") String plant, @Param("delivery") String delivery, @Param("status1") String status1, @Param("status2") String status2);

    List<Header> selecHeaderList(Header header);




}
