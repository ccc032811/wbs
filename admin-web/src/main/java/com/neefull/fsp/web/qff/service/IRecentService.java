package com.neefull.fsp.web.qff.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.web.qff.entity.ProcessHistory;
import com.neefull.fsp.web.qff.entity.Query;
import com.neefull.fsp.web.qff.entity.Recent;
import com.neefull.fsp.web.qff.entity.RecentExcelImport;
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

    /**导出excel
     * @param query
     * @return
     */
    IPage<RecentExcelImport> getRecentExcelImportPage(Query query);
}
