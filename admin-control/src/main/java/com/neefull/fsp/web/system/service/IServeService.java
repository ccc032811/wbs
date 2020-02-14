package com.neefull.fsp.web.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.web.system.entity.ServeMenu;

import java.util.List;

/**
 * @Author: chengchengchu
 * @Date: 2020/2/10  14:31
 */
public interface IServeService extends IService<ServeMenu> {
    /**获取所有的服务
     * @param serveMenu
     * @return
     */
    IPage<ServeMenu> getServeMenus(ServeMenu serveMenu);

    /**更新服务状态
     * @param serveMenu
     */
    void updateStatusById(ServeMenu serveMenu);

    /**新增服务
     * @param serveMenu
     */
    void addServeMenu(ServeMenu serveMenu);

    /**删除服务
     * @param ids
     */
    void deleteServeMenu(String[] ids);

    /**查询所有在线的服务
     * @return
     */
    List<ServeMenu> queryServe();


    /**根据名字查询
     * @param menuName
     * @return
     */
    ServeMenu queryServeByName(String menuName);


}
