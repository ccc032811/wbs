package com.neefull.fsp.web.qff.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.web.common.entity.QueryRequest;
import com.neefull.fsp.web.qff.entity.ProcessHistory;
import com.neefull.fsp.web.qff.entity.Query;
import com.neefull.fsp.web.qff.entity.Roche;
import com.neefull.fsp.web.system.entity.User;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

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
     * @param query
     * @return
     */
    IPage<Roche> getRochePage(Query query);

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
