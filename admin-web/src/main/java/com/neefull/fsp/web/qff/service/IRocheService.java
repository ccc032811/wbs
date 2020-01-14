package com.neefull.fsp.web.qff.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.web.qff.entity.Roche;


/**罗氏内部发起QFF
 * @Author: chengchengchu
 * @Date: 2019/11/29  13:12
 */

public interface IRocheService extends IService<Roche> {


    /**新增罗氏内部发起QFF
     * @param roche
     * @return
     */
    Integer addRoche(Roche roche);

    /**更新罗氏内部发起QFF
     * @param roche
     * @return
     */
    Integer editRoche(Roche roche);

    /**查询罗氏内部QFF
     * @param roche
     * @return
     */
    IPage<Roche> getRochePage(Roche roche);

    /**删除罗氏内部QFF
     * @param id
     * @return
     */
    Integer updateRocheStatus(Integer id,Integer status);

    /**查询罗氏内部QFF
     * @param id
     * @return
     */
    Roche queryRocheById(Integer id);

}
