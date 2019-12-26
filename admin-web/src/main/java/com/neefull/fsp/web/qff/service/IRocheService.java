package com.neefull.fsp.web.qff.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.web.common.entity.QueryRequest;
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

    /**提交流程
     * @param roche
     */
    void commitProcess(Roche roche,User user);

    /**同意当前流程
     * @param roche
     * @param user
     */
    void agreeCurrentProcess(Roche roche, User user);

    /**查询用户当前任务
     * @param user
     * @return
     */
    List<Roche> queryCurrentProcess(User user);

    /**查询当前数据的审核人的信息
     * @param roche
     * @return
     */
    List<String> getGroup(Roche roche);
}
