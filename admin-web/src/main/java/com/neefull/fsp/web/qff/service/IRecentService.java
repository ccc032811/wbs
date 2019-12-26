package com.neefull.fsp.web.qff.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.web.qff.entity.Query;
import com.neefull.fsp.web.qff.entity.Recent;
import com.neefull.fsp.web.system.entity.User;

import java.util.List;

/**近效期QFF
 * @Author: chengchengchu
 * @Date: 2019/11/29  11:31
 */
public interface IRecentService extends IService<Recent> {


    /**近效期QFF
     * @param recent
     * @return
     */
    Integer addRecent(Recent recent);

    /**更新近效期QFF
     * @param recent
     * @return
     */
    Integer editRecent(Recent recent);

    /**查询近效期QFF
     * @param query
     * @return
     */
    IPage<Recent> getRecentPage(Query query);

    /**删除近效期QFF
     * @param id
     * @return
     */
    Integer updateRecentStatus(Integer id,Integer status);

    /**查询近效期QFF
     * @param id
     * @return
     */
    Recent queryRecentById(Integer id);

    /**提交流程
     * @param id
     */
    void commitProcess(Recent recent,User user);

    /**同意当前任务
     * @param recent
     * @param user
     */
    void agreeCurrentProcess(Recent recent, User user);

    /**查询任务
     * @param user
     * @return
     */
    List<Recent> queryCurrentProcess(User user);

    /**查询当前数据在审批的审核人
     * @param recent
     * @return
     */
    List<String> getGroup(Recent recent);
}
