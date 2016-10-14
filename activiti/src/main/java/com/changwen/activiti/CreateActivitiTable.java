package com.changwen.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;
import org.apache.commons.cli.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;


/**
 * <b>function:</b>
 *
 * @author liucw on 2016/10/14.
 */
public class CreateActivitiTable {
    /**
     * 生成23张Activiti表
     */
    @Test
    public void createTable() {
        // 引擎配置
 //       ProcessEngineConfiguration pec=ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        ProcessEngineConfiguration pec = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        //       pec.setJdbcDriver("com.mysql.jdbc.Driver");
//        pec.setJdbcUrl("jdbc:mysql://localhost:3306/db_activiti?useUnicode=true&characterEncoding=utf8");
        pec.setJdbcDriver("oracle.jdbc.OracleDriver");
        pec.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:orcl");

        pec.setJdbcUsername("changwen");
        pec.setJdbcPassword("root");

        /**
         * DB_SCHEMA_UPDATE_FALSE 不能自动创建表，需要表存在
         * create-drop 先删除表再创建表
         * DB_SCHEMA_UPDATE_TRUE 如何表不存在，自动创建和更新表
         */
        pec.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        // 获取流程引擎对象
        ProcessEngine processEngine=pec.buildProcessEngine();
    }

    /**
     * 使用xml配置 简化
     */
    @Test
    public void testCreateTableWithXml(){
        // 引擎配置
        ProcessEngineConfiguration pec=ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        // 获取流程引擎对象
        ProcessEngine processEngine=pec.buildProcessEngine();
    }
}
