package com.neefull.fsp.web.sms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.web.sms.entity.TmsData;

/**
 * @Author: chengchengchu
 * @Date: 2020/12/9  11:42
 */

public interface ITmsDataService extends IService<TmsData> {

    void addTmsData(TmsData tmsData);

    IPage<TmsData> queryTmsList(TmsData tmsData);


}
