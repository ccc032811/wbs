package com.neefull.fsp.web;

import com.neefull.fsp.web.common.controller.BaseController;
import com.neefull.fsp.web.qff.entity.Commodity;
import com.neefull.fsp.web.qff.utils.XmlUtils;
import com.neefull.fsp.web.system.entity.User;
import com.neefull.fsp.web.system.service.IUserService;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.dom4j.DocumentException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: chengchengchu
 * @Date: 2019/12/12  14:05
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplication extends BaseController {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ProcessEngineConfiguration configuration;
    @Autowired
    private IUserService userService;

    /**
     * 初始化表单
     */
    @Test
    public void initTable() {

        //配置数据源
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/ehr?allowMultiQueries=true&useUnicode=true&characterEncoding=utf-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&nullCatalogMeansCurrent=true");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        //注入数据源
        configuration.setDataSource(dataSource);
        //创建配置表的初始化方式
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_CREATE_DROP);

    }

    @Test
    public void initTables2() {
        //必须创建activiti.cfg.xml  并配置好数据库的信息
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    }


    @Test
    public void initBuild01() {
        //部署流程图
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("processes/refund_process.bpmn20.xml")
                .deploy();
        System.out.println(deploy.getId());

    }

  /*  commodityProcess: 到货养护分包出库QFF
    recentProcess: 近效期QFF
    refundProcess: 退货QFF
    rocheProcess: 罗氏内部发起QFF
    */
    @Test
    public void start(){
        String businessKey = Commodity.class.getSimpleName()+":"+14;
        runtimeService.startProcessInstanceByKey("到货养护分包出库QFF",businessKey);
    }


    @Test
    public void dfg(){
        List<User> allUseUserLst = userService.getAllUser();

        System.out.println(allUseUserLst);
    }


    @Test
    public void getItem() throws DocumentException {

        String me = "<ET_QFF><item><QMNUM>000200042850</QMNUM><HERKUNFT>01</HERKUNFT><MAWERK>T028</MAWERK><MATNR>000000000030352227</MATNR><MSTAE/><CHARG>0004151578</CHARG><IDNLF>000000011378783103</IDNLF><BISMT/><LICHN>34140932</LICHN><HSDAT>2019-05-24</HSDAT><VFDAT>2021-05-31</VFDAT><MGEIG>1.000</MGEIG><QMTXT>34140932 1盒变形</QMTXT></item><item><QMNUM>000200042851</QMNUM><HERKUNFT>01</HERKUNFT><MAWERK>T028</MAWERK><MATNR>000000000030351760</MATNR><MSTAE/><CHARG>0004151577</CHARG><IDNLF>000000010413151103</IDNLF><BISMT/><LICHN>37552522</LICHN><HSDAT>2019-05-24</HSDAT><VFDAT>2021-05-31</VFDAT><MGEIG>0.003</MGEIG><QMTXT>37552522  单盒污染</QMTXT></item><item><QMNUM>000200042845</QMNUM><HERKUNFT/><MAWERK>D001</MAWERK><MATNR>000000000020000002</MATNR><MSTAE/><CHARG/><IDNLF/><BISMT/><LICHN/><HSDAT/><VFDAT/><MGEIG>0.000</MGEIG><QMTXT>dagaga</QMTXT></item><item><QMNUM>000200042848</QMNUM><HERKUNFT/><MAWERK>T060</MAWERK><MATNR>000000000030435114</MATNR><MSTAE/><CHARG/><IDNLF/><BISMT/><LICHN/><HSDAT/><VFDAT/><MGEIG>0.000</MGEIG><QMTXT>123</QMTXT></item><item><QMNUM>000200042847</QMNUM><HERKUNFT/><MAWERK>D001</MAWERK><MATNR>000000000020000002</MATNR><MSTAE/><CHARG/><IDNLF/><BISMT/><LICHN/><HSDAT/><VFDAT/><MGEIG>0.000</MGEIG><QMTXT>daa</QMTXT></item><item><QMNUM>000200042841</QMNUM><HERKUNFT/><MAWERK>T060</MAWERK><MATNR>000000000030435114</MATNR><MSTAE/><CHARG/><IDNLF/><BISMT/><LICHN/><HSDAT/><VFDAT/><MGEIG>0.000</MGEIG><QMTXT>ce</QMTXT></item><item><QMNUM>000200042849</QMNUM><HERKUNFT/><MAWERK>T060</MAWERK><MATNR>000000000030435114</MATNR><MSTAE/><CHARG/><IDNLF/><BISMT/><LICHN/><HSDAT/><VFDAT/><MGEIG>0.000</MGEIG><QMTXT>123</QMTXT></item></ET_QFF>";
        String message = XmlUtils.getTagContent(me, "<ET_QFF>", "<ET_QFF>");
        String[] split = message.split("<item>");
        List<String> list = new ArrayList<>();
        for (String s : split) {
            if(StringUtils.isNotEmpty(s)){
                String dom = s.split("</item>")[0];
                list.add(dom);
            }
        }
        for (String s : list) {
            System.out.println(s);
        }
    }


    @Test
    public void getDay(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,-1);
        Date time = c.getTime();
        String format = DateFormatUtils.format(time, "yyyy-MM-dd");

    }


}
