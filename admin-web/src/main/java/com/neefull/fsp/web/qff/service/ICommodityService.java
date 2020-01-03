package com.neefull.fsp.web.qff.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.web.qff.entity.Commodity;
import com.neefull.fsp.web.qff.entity.ProcessHistory;
import com.neefull.fsp.web.qff.entity.Query;
import com.neefull.fsp.web.system.entity.User;
import org.activiti.engine.task.IdentityLink;

import java.util.List;

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
     * @param query
     * @return
     */
    IPage<Commodity> getCommodityPage(Query query);

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

//    /**提交流程
//     * @param commodity
//     */
//    void commitProcess(Commodity commodity, User user);
//
//    /**同意当前任务
//     * @param commodity
//     * @param user
//     */
//    void agreeCurrentProcess(Commodity commodity, User user);
//
//    /**查询用户当前任务
//     * @param user
//     * @return
//     */
//    List<Commodity> queryCurrentProcess(User user);
//
//
//    /**查询流程节点的有资格审核人的信息
//     * @param commodity
//     * @param user
//     * @return
//     */
//    List<String> getGroupId(Commodity commodity, User user);
//
//    /**添加或者更新图片信息
//     * @param commodity
//     * @param user
//     */
//    void addOrEditImage(Commodity commodity, User user);
//
//    /**查询流程
//     * @param id
//     * @return
//     */
//    List<ProcessHistory> queryHistory(Integer id);

}
