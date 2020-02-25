package com.neefull.fsp.web;

import com.neefull.fsp.web.common.controller.BaseController;
import com.neefull.fsp.web.qff.entity.Commodity;
import com.neefull.fsp.web.system.entity.User;
import com.neefull.fsp.web.system.service.IUserService;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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
                .addClasspathResource("processes/roche_process.bpmn20.xml")
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



}
