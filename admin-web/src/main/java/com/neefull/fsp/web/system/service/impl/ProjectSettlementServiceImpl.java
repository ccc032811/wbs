package com.neefull.fsp.web.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.common.util.DateUtils;
import com.neefull.fsp.web.common.utils.FebsUtil;
import com.neefull.fsp.web.system.entity.MsgInfo;
import com.neefull.fsp.web.system.entity.MsgUser;
import com.neefull.fsp.web.system.entity.ProjectSettlement;
import com.neefull.fsp.web.system.entity.User;
import com.neefull.fsp.web.system.mapper.ProjectSettlementMapper;
import com.neefull.fsp.web.system.service.IMsgInfoService;
import com.neefull.fsp.web.system.service.IMsgUserService;
import com.neefull.fsp.web.system.service.IProjectSettlementService;
import com.neefull.fsp.web.system.utils.MsgContentUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Date;

/**
 * @program: fsp
 * @author: xi.chen
 * @create: 2019-09-25 18:34
 **/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ProjectSettlementServiceImpl extends ServiceImpl<ProjectSettlementMapper, ProjectSettlement> implements IProjectSettlementService {

    @Autowired
    private IMsgInfoService msgInfoService;
    @Autowired
    private IMsgUserService msgUserService;

    /**
     * 结算用户
     * @param userId
     * @param projectId
     * @param projectName
     * @param amount
     */
    @Override
    public void settleUserAmount(String userId, String projectId,String projectName, String amount,String settleUserId) {
        this.baseMapper.settleUserAmount(userId, projectId, settleUserId);
        //结算完成后插入一条消息
        MsgInfo msgInfo = MsgContentUtils.getSettleSuccessMsgInfo(projectName, amount);
        msgInfo = msgInfoService.saveReturnPrimaryKey(msgInfo);  //插入操作生成消息id
        //将消息和用户相关联
        msgUserService.saveMsgUserByUserIdAndMsgId(msgInfo.getId(), Long.valueOf(userId));
    }

    /**
     * 批量导入结算人员名单
     * @param file
     */
    @Override
    public JSONObject uploadSettleExcel(MultipartFile file, String filename) {
        boolean isExcel2003 = true;
        JSONObject object = new JSONObject();
        Integer count=0;       //成功导入个数
        Integer filterCount=0; //过滤个数
        Integer line = 1;      //第几行出问题
        try {
            User cuttentUser = FebsUtil.getCurrentUser();  //当前登录的用户

            if (filename.matches("^.+\\.(?i)(xlsx)$")) {
                isExcel2003 = false;
            }
            InputStream is = file.getInputStream();
            Workbook wb = null;
            if (isExcel2003) {
                wb = new HSSFWorkbook(is);
            } else {
                wb = new XSSFWorkbook(is);
            }
            Sheet sheet = wb.getSheetAt(0);
            if (sheet != null) {
                for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                    Row row = sheet.getRow(r);
                    if (row == null) {
                        continue;
                    }

                    line = row.getRowNum()+1;

                    row.getCell(1).setCellType(CellType.STRING);
                    row.getCell(2).setCellType(CellType.STRING);
                    row.getCell(3).setCellType(CellType.STRING);
                    row.getCell(7).setCellType(CellType.STRING);
                    row.getCell(10).setCellType(CellType.STRING);

                    String stateFlag = row.getCell(10).getStringCellValue().substring(2);
                    //只有结算标识为2的才可以录入数据库
                    if(StringUtils.isEmpty(stateFlag)){
                        break;
                    }
                    if(!"2".equals(stateFlag)){
                        filterCount++;
                        continue;
                    }
                    String projectId = row.getCell(1).getStringCellValue().substring(2);
                    String projectName = row.getCell(2).getStringCellValue();
                    String userId = row.getCell(3).getStringCellValue().substring(2);
                    String amount = row.getCell(7).getStringCellValue();

                    //判断该用户是否已结算过
                    String settleFlag = this.baseMapper.getSettleByProjectIdAndUserId(projectId, userId);
                    if("2".equals(settleFlag)){
                        filterCount++;
                        continue;
                    }else{
                        settleUserAmount(userId, projectId, projectName, amount, cuttentUser.getUserId().toString());
                        count++;
                    }
                }
                object.put("result","success");
                object.put("msg","成功导入"+count+"条数据，已过滤"+filterCount+"条数据");
            } else {
                object.put("result","fail");
                object.put("msg","导入失败sheet为空");
            }
        }catch (Exception e){
            object.put("result","fail");
            object.put("msg","导入失败,但已成功导入"+count+"条数据,Excel第"+line+"行学员数据报错："+e);
        }
        return object;
    }
}