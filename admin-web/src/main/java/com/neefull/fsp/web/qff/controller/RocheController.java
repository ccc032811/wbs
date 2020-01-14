package com.neefull.fsp.web.qff.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.neefull.fsp.web.common.controller.BaseController;
import com.neefull.fsp.web.common.entity.FebsResponse;
import com.neefull.fsp.web.common.exception.FebsException;
import com.neefull.fsp.web.qff.entity.ProcessHistory;
import com.neefull.fsp.web.qff.entity.Roche;
import com.neefull.fsp.web.qff.service.IProcessService;
import com.neefull.fsp.web.qff.service.IRocheService;
import com.neefull.fsp.web.qff.utils.ProcessConstant;
import com.neefull.fsp.web.system.entity.User;
import com.wuwenze.poi.ExcelKit;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 罗氏内部发起QFF操作
 *
 * @Author: chengchengchu
 * @Date: 2019/11/29  16:11
 */

@RestController
@RequestMapping("/roche")
public class RocheController extends BaseController {


    @Autowired
    private IRocheService rocheService;
    @Autowired
    private IProcessService processService;

    /**新增罗氏内部发起QFF
     * @param roche
     * @return
     * @throws FebsException
     */
    @PostMapping("/add")
    public FebsResponse addRoche(Roche roche) throws FebsException {
        Integer count = rocheService.addRoche(roche);
        if(count!=1){
            throw new FebsException("新增罗氏内部发起QFF失败");
        }
        return new FebsResponse().success();
    }

    /**更新罗氏内部发起QFF
     * @param roche
     * @return
     * @throws FebsException
     */
    @PostMapping("/edit")
    @RequiresPermissions("roche:audit")
    public FebsResponse editRoche(Roche roche) throws FebsException {
        Integer count = rocheService.editRoche(roche);
        if(count!=1){
            throw new FebsException("更新罗氏内部发起QFF失败");
        }
        return new FebsResponse().success();
    }

    /**查询罗氏内部发起QFF
     * @param roche
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("refund:view")
    public FebsResponse getRochePage(Roche roche){
        IPage<Roche> pageInfo = rocheService.getRochePage(roche);
        Map<String, Object> dataTable = getDataTable(pageInfo);
        return new FebsResponse().success().data(dataTable);
    }

    /**删除罗氏内部QFF
     * @param id
     * @return
     * @throws FebsException
     */
    @GetMapping("/deleteRoche/{id}")
    @RequiresPermissions("refund:del")
    public FebsResponse updateRocheStatus(@PathVariable Integer id) throws FebsException {
        Roche roche = new Roche();
        roche.setId(id);
        processService.deleteInstance(roche);
        Integer count = rocheService.updateRocheStatus(id, ProcessConstant.HAVE_ABNORMAL);
        if(count!=1){
            throw new FebsException("删除罗氏内部QFF失败");
        }
        return new FebsResponse().success();
    }

    /**查询罗氏内部QFF
     * @param id
     * @return
     * @throws FebsException
     */
    @GetMapping("/queryRoche")
    @RequiresPermissions("refund:view")
    public FebsResponse queryRocheById(Integer id) throws FebsException {
        Roche roche = rocheService.queryRocheById(id);
        if(roche==null){
            throw new FebsException("查询罗氏内部发起QFF失败");
        }
        return new FebsResponse().success().data(roche);
    }


    /**查询流程
     * @param roche
     * @return
     */
    @GetMapping("/queryHistory")
    public FebsResponse queryHistory(Roche roche){
        List<ProcessHistory> list = processService.queryHistory(roche);
        return new FebsResponse().success().data(list);
    }

    /**提交流程
     * @param roche
     * @return
     * @throws FebsException
     */
    @PostMapping("/commit")
    @RequiresPermissions("refund:audit")
    public FebsResponse commitProcess(Roche roche) throws FebsException {
        User user = getCurrentUser();
        try {
            processService.commitProcess(roche,user);
        } catch (Exception e) {
            throw new FebsException("提交申请失败");
        }
        return new FebsResponse().success();
    }

    /**同意当前任务
     * @param roche
     * @return
     * @throws FebsException
     */
    @PostMapping("/agree")
    @RequiresPermissions("refund:audit")
    public FebsResponse agreeCurrentProcess(Roche roche) throws FebsException {
        User user = getCurrentUser();
        List<String> group = processService.getGroupId(roche,user);
        if(group.contains(user.getUsername())){
            try {
                processService.agreeCurrentProcess(roche,user);
            } catch (Exception e) {
                throw new FebsException("同意流程失败");
            }
        }else {
            throw new FebsException("当前无权限或改数据已审核");
        }
        return new FebsResponse().success();
    }

    /**导出excel
     * @param roche
     * @param response
     */
    @GetMapping("excel")
    @RequiresPermissions("roche:down")
    public void download(Roche roche, HttpServletResponse response){
        IPage<Roche> rochePage = rocheService.getRochePage(roche);
        List<Roche> rocheList = rochePage.getRecords();
        ExcelKit.$Export(Roche.class, response).downXlsx(rocheList, false);
    }


//    /**查询用户当前任务
//     * @return
//     * @throws FebsException
//     */
//    @GetMapping("/check")
//    @RequiresPermissions("refund:audit")
//    public FebsResponse queryCurrentProcess() throws FebsException {
//        User user = getCurrentUser();
//        List<Roche> list = rocheService.queryCurrentProcess(user);
//        if(CollectionUtils.isEmpty(list)){
//            throw new FebsException("没有任务");
//        }
//        return new FebsResponse().success().data(list);
//    }


}
