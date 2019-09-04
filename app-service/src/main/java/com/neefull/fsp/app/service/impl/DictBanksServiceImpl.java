package com.neefull.fsp.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.app.entity.DictBanks;
import com.neefull.fsp.app.mapper.DictBanksMapper;
import com.neefull.fsp.app.service.IDictBanksService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author pei.wang
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DictBanksServiceImpl extends ServiceImpl<DictBanksMapper, DictBanks> implements IDictBanksService {

}