//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baomidou.dynamic.datasource.plugin;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.ds.GroupDataSource;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.baomidou.dynamic.datasource.support.HealthCheckAdapter;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import java.util.Map;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Lazy;

@Intercepts({@Signature(
	type = Executor.class,
	method = "query",
	args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
), @Signature(
	type = Executor.class,
	method = "query",
	args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}
), @Signature(
	type = Executor.class,
	method = "update",
	args = {MappedStatement.class, Object.class}
)})
public class MasterSlaveAutoRoutingPlugin implements Interceptor {
	private static final Logger log = LoggerFactory.getLogger(MasterSlaveAutoRoutingPlugin.class);
	@Autowired
	protected DataSource dynamicDataSource;
	@Autowired
	private DynamicDataSourceProperties properties;
	@Lazy
	@Autowired(
		required = false
	)
	private HealthCheckAdapter healthCheckAdapter;

	@Value("${spring.datasource.dynamic.log}")
	private boolean masterLog;

	public MasterSlaveAutoRoutingPlugin() {
	}

	public Object intercept(Invocation invocation) throws Throwable {
		Object[] args = invocation.getArgs();
		MappedStatement ms = (MappedStatement)args[0];
		String pushedDataSource = null;

		Object var6;
		try {
			String dataSource = this.getDataSource(ms);
			pushedDataSource = DynamicDataSourceContextHolder.push(dataSource);
			var6 = invocation.proceed();
			if (masterLog){
				logMasterAndSlave(ms,pushedDataSource);
			}
		} finally {
			if (pushedDataSource != null) {
				DynamicDataSourceContextHolder.poll();
			}

		}

		return var6;
	}

	public String getDataSource(MappedStatement mappedStatement) {
		String currentDataSource = SqlCommandType.SELECT == mappedStatement.getSqlCommandType() ? "slave" : "master";
		String dataSource = null;
		if (this.properties.isHealth()) {
			DynamicRoutingDataSource dynamicRoutingDataSource = (DynamicRoutingDataSource)this.dynamicDataSource;
			Map currentGroupDataSources;
			GroupDataSource groupDataSource;
			if ("slave".equalsIgnoreCase(currentDataSource)) {
				currentGroupDataSources = dynamicRoutingDataSource.getCurrentGroupDataSources();
				groupDataSource = (GroupDataSource)currentGroupDataSources.get("slave");
				String dsKey = groupDataSource.determineDsKey();
				boolean health = this.healthCheckAdapter.getHealth(dsKey);
				if (health) {
					dataSource = dsKey;
				} else {
					log.warn("从库无法连接, 请检查数据库配置, key: {}", dsKey);
				}
			}

			if (dataSource == null) {
				currentGroupDataSources = dynamicRoutingDataSource.getCurrentGroupDataSources();
				groupDataSource = (GroupDataSource)currentGroupDataSources.get("master");
				dataSource = groupDataSource.determineDsKey();
				boolean health = this.healthCheckAdapter.getHealth(dataSource);
				if (!health) {
					log.warn("主库无法连接, 请检查数据库配置, key: {}", dataSource);
				}
			}
		} else {
			dataSource = currentDataSource;
		}

		return dataSource;
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
	}

	public void logMasterAndSlave(MappedStatement ms,String pushedDataSource){
//		String logString = ms.getId().substring(ms.getId().lastIndexOf(".")+1);
		log.info("查询方法:"+ms.getId()+"----"+"数据源:"+pushedDataSource);
	}
}
