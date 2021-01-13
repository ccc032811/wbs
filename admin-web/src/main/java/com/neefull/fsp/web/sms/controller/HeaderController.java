package com.neefull.fsp.web.sms.controller;

import com.neefull.fsp.web.common.controller.BaseController;
import com.neefull.fsp.web.common.entity.FebsResponse;
import com.neefull.fsp.web.common.exception.FebsException;
import com.neefull.fsp.web.sms.entity.Detail;
import com.neefull.fsp.web.sms.entity.Header;
import com.neefull.fsp.web.sms.entity.vo.HeaderVo;
import com.neefull.fsp.web.sms.service.IHeaderService;
import com.neefull.fsp.web.sms.service.IScanService;
import com.neefull.fsp.web.sms.utils.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: chengchengchu
 * @Date: 2020/11/24  14:07
 */
@Slf4j
@RestController
@RequestMapping("/scan/smsHeader")
public class HeaderController extends BaseController {

    @Autowired
    private IHeaderService headerService;
    @Autowired
    private IScanService scanService;


    /**查询DN Header
     * @param header
     * @return
     * @throws FebsException
     */
    @GetMapping("/queryHeaderList")
    public FebsResponse queryHeaderList(Header header) throws FebsException {
        try {
            Map<String, Object> dataTable = getDataTable(headerService.queryHeaderList(header));
            return new FebsResponse().success().data(dataTable);
        } catch (Exception e) {
            String message = "查询DN Header表失败！";
            log.error(message+"失败原因为: {}",e);
            throw new FebsException(message);
        }
    }



    /**根据DN号查询
     * @param delivery
     * @return
     * @throws FebsException
     */
    @GetMapping("/sms/header/{delivery}")
    public FebsResponse queryHeaderByDelivery(@PathVariable String delivery) throws FebsException {
        try {
            Header header = headerService.queryHeaderByDelivery(delivery);
            return new FebsResponse().success().data(header);
        } catch (Exception e) {
            String message = "查询DN Header失败！";
            log.error(message+"失败原因为: {}",e);
            throw new FebsException(message);
        }
    }


    /**查询已扫但是没审核和审核未通过的dn
     * @param plant
     * @return
     */
    @GetMapping("/queryScanDn/{delivery}")
    public FebsResponse queryScanDn(@PathVariable String delivery) throws FebsException {
        try {
            HeaderVo headerVo = headerService.queryScanDn(delivery);
            return new FebsResponse().success().data(headerVo);
        } catch (Exception e) {
            String message = "查询DN失败！";
            log.error(message+"失败原因为: {}",e);
            throw new FebsException(message);
        }
    }


    /**查询三个状态的数量
     * @return
     * @throws FebsException
     */
    @GetMapping("/queryScanNumber")
    public FebsResponse queryScanNumber(Header header) throws FebsException {
        try {
            Map<String,Integer> num = headerService.queryScanNumber(header);
            return new FebsResponse().success().data(num);
        } catch (Exception e) {
            String message = "查询DN失败！";
            log.error(message+"失败原因为: {}",e);
            throw new FebsException(message);
        }
    }



    /**查询扫描对比
     * @param header
     * @return
     * @throws FebsException
     */
    @GetMapping("/queryCompareList")
    public FebsResponse queryCompareList(Header header) throws FebsException {
        try {
            Map<String, Object> dataTable = getDataTable(headerService.queryCompareList(header));
            return new FebsResponse().success().data(dataTable);
        } catch (Exception e) {
            String message = "查询扫描对比表失败！";
            log.error(message+"失败原因为: {}",e);
            throw new FebsException(message);
        }
    }



    @GetMapping("/headerExcel")
    public void headerExcel(Header header, HttpServletResponse response) throws FebsException {

        try {
            List<Header> headerList = headerService.getHeaderExcel(header);

            SXSSFWorkbook workbook = new SXSSFWorkbook();
            SXSSFSheet sheet = workbook.createSheet("DN信息");
            String[] nameList = new String[]{"DN号", "售达方代码", "售达方名称", "送达方代码","送达方名称","送货地址","销售订单号","药厂DO号",
                    "药厂送达方代码","药厂送达方名称","药厂订单号","DN号","物料代码","药厂物料代码","物料描述","物料英文描述","药厂物料描述",
                    "工厂","数量","单位","药厂单位","有效期","是否批次管理","是否序列号管理","是否有效期管理","存储条件","存储条件描述"};
            List<Object[]> dataList = new ArrayList<Object[]>();
            Object[] objs = null;
            for (Header head : headerList) {
                List<Detail> detailList = head.getDetailList();
                for (Detail detail : detailList) {
                    objs = new Object[nameList.length];
                    objs[0] = head.getDelivery();
                    objs[1] = head.getSoldParty();
                    objs[2] = head.getSoldPartyName();
                    objs[3] = head.getShipParty();
                    objs[4] = head.getShipPartyName();
                    objs[5] = head.getAddress();
                    objs[6] = head.getSalesOrder();
                    objs[7] = head.getRocheDelivery();
                    objs[8] = head.getRocheShipParty();
                    objs[9] = head.getRocheShipPartyName();
                    objs[10] = head.getRocheOrder();
                    objs[11] = detail.getDelivery();
                    objs[12] = detail.getMaterial();
                    objs[13] = detail.getRocheMaterial();
                    objs[14] = detail.getMaterialDescription();
                    objs[15] = detail.getMaterialDescriptionEn();
                    objs[16] = detail.getRocheMaterialDescription();
                    objs[17] = detail.getPlant();
                    objs[18] = detail.getQuantity();
                    objs[19] = detail.getUom();
                    objs[20] = detail.getRocheUom();
                    objs[21] = detail.getExpiryDate();
                    objs[22] = detail.getIfBatchManagement();
                    objs[23] = detail.getIfSerialNumberManagement();
                    objs[24] = detail.getIfExpiryDateManagement();
                    objs[25] = detail.getSaveMode();
                    objs[26] = detail.getSaveModeDescription();
                    dataList.add(objs);
                }
            }
            ExcelUtil ex = new ExcelUtil(nameList, dataList);
            ex.exportExcel(workbook,sheet,response);
        } catch (Exception e) {
            String message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @GetMapping("/compareExcel")
    public void getCompareExcel(Header header,HttpServletResponse response) throws FebsException {

        try {
            List<Header> headerList = headerService.getCompareExcel(header);

            SXSSFWorkbook workbook = new SXSSFWorkbook();
            SXSSFSheet sheet = workbook.createSheet("扫描对比信息");
            String[] nameList = new String[]{"DN号", "售达方代码", "送达方代码","送货地址","工厂","错误信息"
                    ,"DN号","物料号","箱码","数量","已扫数量","有效期","错误信息"};
            List<Object[]> dataList = new ArrayList<Object[]>();
            Object[] objs = null;
            for (Header head : headerList) {
                List<Detail> detailList = head.getDetailList();
                for (Detail detail : detailList) {
                    List<String> boxTypeList = scanService.queryBoxTypeByDeliveryAndMatCode(detail.getDelivery(),detail.getMaterial());
                    objs = new Object[nameList.length];
                    objs[0] = head.getDelivery();
                    objs[1] = head.getSoldParty();
                    objs[2] = head.getShipParty();
                    objs[3] = head.getAddress();
                    objs[4] = head.getPlant();
                    objs[5] = head.getErrorMessage();
                    objs[6] = detail.getDelivery();
                    objs[7] = detail.getMaterial();
                    objs[8] = StringUtils.join(boxTypeList,", ");
                    objs[9] = detail.getQuantity();
                    objs[10] = detail.getScanQuantity();
                    objs[11] = detail.getExpiryDate();
                    objs[12] = detail.getErrorMessage();
                    dataList.add(objs);
                }
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
