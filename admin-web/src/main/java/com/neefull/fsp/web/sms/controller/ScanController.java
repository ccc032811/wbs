package com.neefull.fsp.web.sms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.neefull.fsp.web.common.annotation.Log;
import com.neefull.fsp.web.common.controller.BaseController;
import com.neefull.fsp.web.common.entity.FebsResponse;
import com.neefull.fsp.web.common.exception.FebsException;
import com.neefull.fsp.web.sms.entity.*;
import com.neefull.fsp.web.sms.entity.vo.DetailScanVo;
import com.neefull.fsp.web.sms.entity.vo.HeaderVo;
import com.neefull.fsp.web.sms.service.IScanLogService;
import com.neefull.fsp.web.sms.service.IScanService;
import com.neefull.fsp.web.sms.utils.ExcelUtil;
import com.neefull.fsp.web.sms.utils.SoapProperties;
import com.neefull.fsp.web.system.entity.Opinion;
import com.neefull.fsp.web.system.entity.User;
import com.neefull.fsp.web.system.service.IOpinionService;
import com.neefull.fsp.web.system.service.IUserService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**扫描
 * @Author: chengchengchu
 * @Date: 2020/11/24  14:29
 */

@Slf4j
@Controller
@RequestMapping("/scan/smsScan")
public class ScanController extends BaseController {


    @Autowired
    private IScanService scanService;
    @Autowired
    private IScanLogService scanLogService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IOpinionService opinionService;


    /**根据DN号去soap查询对应的信息
     * @param delivery
     * @return
     * @throws FebsException
     */
    @Log("获取DN信息")
    @GetMapping("/getDnMessage/{delivery}")
    @ResponseBody
    public FebsResponse getDnMessageByDelivery(@PathVariable String delivery) throws FebsException {

        try {
            DetailScanVo dnMessage = scanLogService.getDnMessageByDelivery(delivery);
            return new FebsResponse().success().data(dnMessage);
        } catch (Exception e) {
            String message = "根据DN号查询失败！";
            log.error(message+"失败原因为: {}",e);
            throw new FebsException(message);
        }
    }


    /**审核DN    返回是否通过  map
     * @param dns
     * @return
     * @throws FebsException
     */
    @Log("审核DN")
    @GetMapping("/auditDn/{dns}/{userName}")
    @ResponseBody
    public FebsResponse auditDn(@PathVariable String dns,@PathVariable String userName) throws FebsException {
        try {
            List<HeaderVo> headerVos = scanService.auditDn(dns,userName);
            return new FebsResponse().success().data(headerVos);
        } catch (Exception e) {
            String message = "审核DN失败！";
            log.error(message+"失败原因为: {}",e);
            throw new FebsException(message);
        }
    }



    /**新增扫描记录信息
     * @return
     */
    @Log("新增扫描记录")
    @PostMapping("/insertScanMsg")
    @ResponseBody
    public FebsResponse insertScanMsg(ScanAdd scanAdd) throws FebsException {
        try {
            scanService.insertScanMsg(scanAdd);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "新增扫描记录信息失败！";
            log.error(message+"失败原因为: {}",e);
            throw new FebsException(message);
        }
    }


    /**分页查询扫描记录
     * @param scan
     * @return
     * @throws FebsException`
     */
    @GetMapping("/getScanInfoList")
    @ResponseBody
    public FebsResponse getScanInfoList(Scan scan) throws FebsException {
        try {
            IPage<Scan> headerList = scanService.getScanInfoList(scan);
            return new FebsResponse().success().data(getDataTable(headerList));
        } catch (Exception e) {
            String message = "查询扫描记录信息分页失败！";
            log.error(message +"失败原因为: {}",e);
            throw new FebsException(message);
        }
    }


    /**查询扫描记录信息
     * @param scan
     * @return
     * @throws FebsException
     */
    @GetMapping("/queryScanDetailList")
    @ResponseBody
    public FebsResponse queryScanDetailList(Scan scan) throws FebsException {
        try {
            IPage<Scan> scanDetailList = scanService.queryScanDetailList(scan);
            return new FebsResponse().success().data(getDataTable(scanDetailList));
        } catch (Exception e) {
            String message = "查询扫描记录信息失败！";
            log.error(message +"失败原因为: {}",e);
            throw new FebsException(message);
        }
    }


    /**查询DN获取记录集合   sms_scan_log
     * @param scanLog
     * @return
     * @throws FebsException
     */
    @GetMapping("/queryScanLogList")
    @ResponseBody
    public FebsResponse queryScanLogList(ScanLog scanLog) throws FebsException {
        try {
            Map<String, Object> dataTable = getDataTable(scanLogService.queryScanLogList(scanLog));
            return new FebsResponse().success().data(dataTable);
        } catch (Exception e) {
            String message = "查询已获取DN数据失败！";
            log.error(message +"失败原因为: {}",e);
            throw new FebsException(message);
        }
    }


    /**修改扫描信息
     * @return
     * @throws FebsException
     */
    @Log("修改扫描信息")
    @PostMapping("/editScanDetail")
    @ResponseBody
    public FebsResponse editScanDetail(ScanAdd scanAdd) throws FebsException {
        try {
            scanService.editScanDetail(scanAdd.getScanList());
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "查询已获取DN数据失败！";
            log.error(message +"失败原因为: {}",e);
            throw new FebsException(message);
        }
    }


    /**获取所有的扫描记录  并获取实际扫描数量
     * @param delivery
     * @return
     * @throws FebsException
     */
    @GetMapping("/getScanList/{delivery}")
    @ResponseBody
    public FebsResponse getScanList(@PathVariable String delivery) throws FebsException {
        try {
            List<Scan> scanList = scanService.queryScanAndCountByDelivery(delivery);
            return new FebsResponse().success().data(scanList);
        } catch (Exception e) {
            String message = "查询已获取DN数据失败！";
            log.error(message +"失败原因为: {}",e);
            throw new FebsException(message);
        }
    }



    /**删除扫描信息   扫码枪
     * @param delivery
     * @return
     * @throws FebsException
     */
    @Log("重扫删除扫描信息")
    @GetMapping("/deleteScanDetail/{delivery}")
    @ResponseBody
    public FebsResponse deleteScanDetail(@PathVariable String delivery) throws FebsException {
        try {
            scanService.deleteScanDetail(delivery);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "删除已扫DN数据失败！";
            log.error(message +"失败原因为: {}",e);
            throw new FebsException(message);
        }
    }

    //PC
    @Log("根据id删除扫描信息")
    @GetMapping("/deleteScanById/{delivery}/{id}")
    @ResponseBody
    public FebsResponse deleteScanById(@PathVariable String delivery,@PathVariable Integer id) throws FebsException {
        try {
            scanService.deleteScanById(id,delivery);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "根据id删除扫描信息失败！";
            log.error(message +"失败原因为: {}",e);
            throw new FebsException(message);
        }
    }


    /**获取人员操作类型   按箱扫码    按码扫箱
     * @param username
     * @return
     */
    @GetMapping("/getScanType/{username}")
    @ResponseBody
    public FebsResponse getScanType(@PathVariable String username) throws FebsException {
        try {
            String type = userService.getScanType(username);
            return new FebsResponse().success().data(type);
        } catch (Exception e) {
            String message = "获取人员操作类型失败！";
            log.error(message +"失败原因为: {}",e);
            throw new FebsException(message);
        }
    }


    /**获取箱号位数
     * @return
     */
    @GetMapping("/getScanBoxNum")
    @ResponseBody
    public FebsResponse getScanBoxNum() throws FebsException {
        try {
            String name = opinionService.queryOpionById(SoapProperties.BOXTYPE);
            return new FebsResponse().success().data(name);
        } catch (Exception e) {
            String message = "获取箱号位数失败！";
            log.error(message +"失败原因为: {}",e);
            throw new FebsException(message);
        }
    }


    @GetMapping("/scanExcel")
    public void getScanExcel(Scan scan ,HttpServletResponse response) throws FebsException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            SXSSFWorkbook workbook = new SXSSFWorkbook();
            SXSSFSheet sheet = workbook.createSheet("扫描记录");

            String[] nameList = new String[]{"DN号", "箱型", "箱码", "物料号","物料名","条形码"
                    ,"数量","有效期","单位","扫描人","扫描时间"};
            List<Object[]> dataList = new ArrayList<Object[]>();
            Object[] objs = null;
            List<Scan> scanList = scanService.downScanExcel(scan);
            for (Scan sca : scanList) {
                objs = new Object[nameList.length];
                objs[0] = sca.getDelivery();
                objs[1] = sca.getBoxType();
                objs[2] = sca.getBoxCode();
                objs[3] = sca.getMatCode();
                objs[4] = sca.getMatName();
                objs[5] = sca.getSerialNumber();
                objs[6] = sca.getQuantity();
                objs[7] = sca.getExpiryDate();
                objs[8] = sca.getUnit();
                objs[9] = sca.getScanUser();
                objs[10] = simpleDateFormat.format(sca.getCreateTime());
                dataList.add(objs);
            }
            ExcelUtil ex = new ExcelUtil(nameList, dataList);
            ex.exportExcel(workbook,sheet,response);
        } catch (Exception e) {
            String message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }


    }


}
