package com.ruoyi.dbu.config;

import liquibase.integration.spring.SpringLiquibase;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfig
{
    @Value("${update.module:}")
    private String module;

    @Value("${liquibase.schema:}")
    private String liquibaseSchema;

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource)
        throws Exception
    {
        if (StringUtils.isEmpty(module) || module.equals("''"))
        {
            throw new Exception("请配置--update.module=oms/wms/admin(只可配置一个),选择需要更新的数据库");
        }

        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:" + module + "/master.xml");
        liquibase.setContexts("development,test,production");
        liquibase.setShouldRun(true);
        liquibase.setDatabaseChangeLogTable("ds_changelog");
        liquibase.setDatabaseChangeLogLockTable("ds_changelog_lock");
//        liquibase.setDefaultSchema(null);
        if (!StringUtils.isEmpty(liquibaseSchema))
        {
            liquibase.setLiquibaseSchema(liquibaseSchema);
        }

        return liquibase;
    }

}
