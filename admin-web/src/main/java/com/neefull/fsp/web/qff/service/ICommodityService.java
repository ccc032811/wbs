package com.neefull.fsp.web.qff.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.web.qff.entity.Commodity;


/**
 * @Author: chengchengchu
 * @Date: 2019/12/6  18:52
 */

public interface ICommodityService extends IService<Commodity> {


    /**新增养护QFF
     * @param commodity
     * @return
     */
    Integer addCommodity(Commodity commodity);

    /**更新养护QFF
     * @param commodity
     * @return
     */
    Integer editCommodity(Commodity commodity);

    /**获取养护操作的信息
     * @param commodity
     * @return
     */
    IPage<Commodity> getCommodityPage(Commodity commodity);

    /**删除养护QFF
     * @param id
     * @return
     */
    Integer updateCommodityStatus(Integer id,Integer status);

    /**根据编号查询
     * @param id
     * @return
     */
    Commodity queryCommodityById(Integer id);


    /**根据QFF编号查询
     * @param number
     * @return
     */
    Commodity queryCommodityByNumber(String number);
}
