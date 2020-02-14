package com.neefull.fsp.web.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neefull.fsp.web.system.entity.ServeMenu;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: chengchengchu
 * @Date: 2020/2/10  14:34
 */
@Component
public interface ServeMapper extends BaseMapper<ServeMenu> {


    /**获取所有的服务
     * @param page
     * @param serveMenu
     * @return
     */
    IPage<ServeMenu> getServeMenu(Page<ServeMenu> page, ServeMenu serveMenu);

    /**更新服务状态
     *
     * @param serveMenu
     */
    void updateStatusById(ServeMenu serveMenu);

    /**查询所有在线的服务
     * @return
     */
    List<ServeMenu> queryServe();

}
