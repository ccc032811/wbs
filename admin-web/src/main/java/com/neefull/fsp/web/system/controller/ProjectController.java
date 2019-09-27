package com.neefull.fsp.web.system.controller;

import com.neefull.fsp.web.common.annotation.Log;
import com.neefull.fsp.web.common.controller.BaseController;
import com.neefull.fsp.web.common.entity.FebsResponse;
import com.neefull.fsp.web.common.entity.QueryRequest;
import com.neefull.fsp.web.common.exception.FebsException;
import com.neefull.fsp.web.system.entity.Project;
import com.neefull.fsp.web.system.entity.ProjectSettlement;
import com.neefull.fsp.web.system.service.IProjectService;
import com.neefull.fsp.web.system.service.IProjectSettlementService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: fsp
 * @author: xi.chen
 * @create: 2019-09-23 15:01
 **/
@Slf4j
@Validated
@RestController
@RequestMapping("project")
public class ProjectController extends BaseController {

    @Autowired
    private IProjectService projectService;
    @Autowired
    private IProjectSettlementService projectSettlementService;

    /**
     * 项目管理-列表查询页面
     * @param project 项目实体类
     * @param request 请求
     * @return 项目信息列表数据
     */
    @GetMapping("list")
    @RequiresPermissions("project:view")
    public FebsResponse projectList(Project project, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(this.projectService.findProjectBySearch(project, request));
        return new FebsResponse().success().data(dataTable);
    }

    /**
     * 查询某项目的结算人员列表
     * @param settlement
     * @param request
     * @return
     */
    @GetMapping("settle")
    @RequiresPermissions("project:settle")
    public FebsResponse settleList(ProjectSettlement settlement, QueryRequest request,String projectId) {
        if(settlement.getProjectId() == 0){
            settlement.setProjectId(Long.valueOf(projectId));
        }
        Map<String, Object> dataTable = getDataTable(this.projectService.findProjectSettleByProjectId(settlement, request));
        return new FebsResponse().success().data(dataTable);
    }

    /**
     * 项目列表页面导出Excel
     * @param queryRequest 请求
     * @param project 查询条件实体
     * @param response response
     * @throws FebsException exception
     */
    @GetMapping("excel")
    @RequiresPermissions("project:export")
    public void export(QueryRequest queryRequest, Project project, HttpServletResponse response) throws FebsException {
        try {
            List<Project> projects = this.projectService.findProjectBySearch(project, queryRequest).getRecords();
            ExcelKit.$Export(Project.class, response).downXlsx(projects, false);
        }catch (Exception e){
            String message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("settleExcel")
    @RequiresPermissions("project:settle")
    public void settleExcel(QueryRequest queryRequest, ProjectSettlement settlement, HttpServletResponse response) throws FebsException {
        try {
            List<ProjectSettlement> settlements = this.projectService.findProjectSettleByProjectId(settlement, queryRequest).getRecords();
            ExcelKit.$Export(ProjectSettlement.class, response).downXlsx(settlements, false);
//            //制作excel
//            HSSFWorkbook workbook = new HSSFWorkbook();
//            HSSFSheet sheet = workbook.createSheet("网授课程未签到学员名单");
//            PrintSetup printSetup = sheet.getPrintSetup();
//            printSetup.setLandscape(true);
//            sheet.setFitToPage(true);  // 是否自适应界面
//            sheet.setHorizontallyCenter(true);
//
//            sheet.setColumnWidth(0, 20*256);
//            sheet.setColumnWidth(1, 8*256);
//            sheet.setColumnWidth(2, 20*256);
//            sheet.setColumnWidth(3, 8*256);
//            sheet.setColumnWidth(4, 8*256);
//            sheet.setColumnWidth(5, 20*256);
//            sheet.setColumnWidth(6, 20*256);
//            sheet.setColumnWidth(7, 8*256);
//            sheet.setColumnWidth(8, 20*256);
//            sheet.setColumnWidth(9, 20*256);
//            sheet.setColumnWidth(10, 20*256);
//            sheet.setColumnWidth(11, 8*256);
//
//            Row titleRow = sheet.createRow(0);
//            List<String> titleContent=new ArrayList<String>();
//            titleContent.add("公司名称");
//            titleContent.add("项目编号");
//            titleContent.add("项目名称");
//            titleContent.add("用户id");
//            titleContent.add("用户真实姓名");
//            titleContent.add("用户手机号");
//            titleContent.add("用户身份证");
//            titleContent.add("结算总金额");
//            titleContent.add("发起结算时间");
//            titleContent.add("卡号");
//            titleContent.add("备注");
//            titleContent.add("结算状态(0:待结算;1:结算完成)");
//            for (int i = 0; i < 12; i++) {
//                titleRow.createCell(i);
//            }
//            for(int j=0;j<titleContent.size();j++){
//                titleRow.getCell(j).setCellValue(titleContent.get(j));
//            }
//            for(int m=0;m<settlements.size();m++){
//                Row dataRow =sheet.createRow(1+m);
//                for(int n=0;n< 15;n++){
//                    dataRow.createCell(n);
//                }
//                dataRow.getCell(0).setCellValue(settlements.get(m).getCorpName());
//                dataRow.getCell(1).setCellValue(settlements.get(m).getProjectId());
//                dataRow.getCell(2).setCellValue(settlements.get(m).getProjectName());
//                dataRow.getCell(3).setCellValue(settlements.get(m).getUserId());
//                dataRow.getCell(4).setCellValue(settlements.get(m).getUserName());
//                dataRow.getCell(5).setCellValue(settlements.get(m).getMobile());
//                dataRow.getCell(6).setCellValue(settlements.get(m).getIdNo());
//                dataRow.getCell(7).setCellValue(settlements.get(m).getSettleAmount());
//                dataRow.getCell(8).setCellValue(settlements.get(m).getSettleTimeName());
//                dataRow.getCell(9).setCellValue(settlements.get(m).getCardNo());
//                dataRow.getCell(10).setCellValue(settlements.get(m).getReamark());
//                dataRow.getCell(11).setCellValue(settlements.get(m).getState());
//            }
//            String fileName="结算人员名单.xls";
//            response.setContentType("multipart/form-data");
//            response.setHeader("Content-Disposition", "attachment;fileName="+ new String(fileName.getBytes(), "ISO8859-1"));
//            ServletOutputStream out=response.getOutputStream();
//            workbook.write(out);
//            out.close();
        }catch (Exception e){
            String message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("结算用户")
    @GetMapping("settleUser")
    @RequiresPermissions("project:settle")
    public FebsResponse settleUser(String userId, String projectId) throws FebsException {
        try {
            projectSettlementService.settleUserAmount(userId, projectId);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "操作失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
}