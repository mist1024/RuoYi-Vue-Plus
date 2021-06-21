package com.ruoyi.framework.config;

import com.baomidou.dynamic.datasource.plugin.MasterSlaveAutoRoutingPlugin;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 在纯的读写分离环境，写操作全部是master，读操作全部是slave
 * @author ding
 */
@Configuration
public class DynamicDataSourceConfig {

	@Bean
	public MasterSlaveAutoRoutingPlugin masterSlaveAutoRoutingPlugin(){
		return new MasterSlaveAutoRoutingPlugin();
	}

	//    /**
//     * 去除监控页面底部的广告
//     */
//    @SuppressWarnings({ "rawtypes", "unchecked" })
//    @Bean
//    @ConditionalOnProperty(name = "spring.datasource.dynamic.druid.statViewServlet.enabled", havingValue = "true")
//    public FilterRegistrationBean removeDruidFilterRegistrationBean(DruidStatProperties properties)
//    {
//        // 获取web监控页面的参数
//        DruidStatProperties.StatViewServlet config = properties.getStatViewServlet();
//        // 提取common.js的配置路径
//        String pattern = config.getUrlPattern() != null ? config.getUrlPattern() : "/druid/*";
//        String commonJsPattern = pattern.replaceAll("\\*", "js/common.js");
//        final String filePath = "support/http/resources/js/common.js";
//        // 创建filter进行过滤
//        Filter filter = new Filter()
//        {
//            @Override
//            public void init(javax.servlet.FilterConfig filterConfig) throws ServletException
//            {
//            }
//            @Override
//            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//                    throws IOException, ServletException
//            {
//                chain.doFilter(request, response);
//                // 重置缓冲区，响应头不会被重置
////                response.resetBuffer();
//                // 获取common.js
//                String text = Utils.readFromResource(filePath);
//                // 正则替换banner, 除去底部的广告信息
//                text = text.replaceAll("<a.*?banner\"></a><br/>", "");
//                text = text.replaceAll("powered.*?shrek.wang</a>", "");
//                response.getWriter().write(text);
//            }
//            @Override
//            public void destroy()
//            {
//            }
//        };
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        registrationBean.setFilter(filter);
//        registrationBean.addUrlPatterns(commonJsPattern);
//        return registrationBean;
//    }

}
