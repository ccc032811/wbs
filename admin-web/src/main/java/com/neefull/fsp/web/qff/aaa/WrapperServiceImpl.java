/*
package com.neefull.fsp.web.qff.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.web.qff.entity.Query;
import com.neefull.fsp.web.qff.aaa.Wrapper;
import com.neefull.fsp.web.qff.aaa.WrapperMapper;
import com.neefull.fsp.web.qff.aaa.IWrapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

*/
/**
 * @Author: chengchengchu
 * @Date: 2019/12/9  16:10
 *//*



@Service
public class WrapperServiceImpl extends ServiceImpl<WrapperMapper, Wrapper> implements IWrapperService {

    @Autowired
    private WrapperMapper wrapperMapper;

    @Override
    public Integer addWrapper(Wrapper wrapper) {
        int count = wrapperMapper.insert(wrapper);
        return count;
    }

    @Override
    public Integer editWrapper(Wrapper wrapper) {
        UpdateWrapper<Wrapper> uppdte = new UpdateWrapper<>();
        uppdte.eq("number",wrapper.getNumber());
        int count = wrapperMapper.update(wrapper, uppdte);
        return count;
    }

    @Override
    public IPage<Wrapper> getWrapperPage(Query query) {
        Page<Wrapper> page = new Page<>(query.getPageNum(),query.getPageSize());
        IPage<Wrapper> pageInfo = wrapperMapper.getWrapperPage(page,query);
        return pageInfo;
    }

    @Override
    public Integer deleteWrapper(String number) {
        Integer count = wrapperMapper.deleteWrapper(number,4);
        return count;

    }

    @Override
    public Wrapper queryWrapperByNumber(String number) {
        QueryWrapper<Wrapper> query = new QueryWrapper<>();
        query.eq("number",number);
        Wrapper wrapper = wrapperMapper.selectOne(query);
        return wrapper;
    }
}
*/
