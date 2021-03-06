package com.neefull.fsp.web.job.task;

import com.neefull.fsp.web.sms.entity.copy.DetailCopy;
import com.neefull.fsp.web.sms.entity.copy.HeaderCopy;
import com.neefull.fsp.web.sms.entity.copy.ScanCopy;
import com.neefull.fsp.web.sms.mapper.copy.DetailCopyMapper;
import com.neefull.fsp.web.sms.mapper.copy.HeaderCopyMapper;
import com.neefull.fsp.web.sms.mapper.copy.ScanCopyMapper;
import com.neefull.fsp.web.sms.service.IDetailService;
import com.neefull.fsp.web.sms.service.IHeaderService;
import com.neefull.fsp.web.sms.service.IScanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**数据备份
 * @Author: chengchengchu
 * @Date: 2021/1/11  15:26
 */

@Slf4j
@Component
public class WbsDateCopy {

    @Autowired
    private HeaderCopyMapper headerCopyMapper;
    @Autowired
    private DetailCopyMapper detailCopyMapper;
    @Autowired
    private ScanCopyMapper scanCopyMapper;
    @Autowired
    private IHeaderService headerService;
    @Autowired
    private IDetailService detailService;
    @Autowired
    private IScanService scanService;


    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public void dateCopy(String dayCount){
        //这个时间往前推一个月的时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE,Integer.parseInt("-"+dayCount));
        Date time = calendar.getTime();

        try {
            //获取一个月前这个点的数据
            List<HeaderCopy> headerCopyList = headerCopyMapper.selectCopyDateByTime(time);
            for (HeaderCopy headerCopy : headerCopyList) {
                headerCopyMapper.insert(headerCopy);
                List<DetailCopy> detailCopyList = detailCopyMapper.selectCopyDetailByDelivery(headerCopy.getDelivery());
                List<ScanCopy> scanCopyList = scanCopyMapper.selectCopyScanByDelivery(headerCopy.getDelivery());
                for (DetailCopy detailCopy : detailCopyList) {
                    detailCopyMapper.insert(detailCopy);
                }
                for (ScanCopy scanCopy : scanCopyList) {
                    scanCopyMapper.insert(scanCopy);
                }

                for (DetailCopy detailCopy : detailCopyList) {
                    detailService.deleteByDelivery(detailCopy.getDelivery());
                }
                for (ScanCopy scanCopy : scanCopyList) {
                    scanService.deleteByDelivery(scanCopy.getDelivery());
                }
            }

            for (HeaderCopy headerCopy : headerCopyList) {
                headerService.deleteByDelivery(headerCopy.getDelivery());
            }
        } catch (Exception e) {
            log.error("备份数据失败，失败原因为：{}",e.getMessage());
            e.printStackTrace();
        }


    }

}
