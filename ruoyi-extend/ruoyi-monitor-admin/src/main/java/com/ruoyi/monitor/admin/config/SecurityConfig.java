package com.ruoyi.monitor.admin.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.sql.DataSource;

/**
 * admin 监控 安全配置
 *
 * @author Lion Li
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, proxyTargetClass = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final String adminContextPath;

	public SecurityConfig(AdminServerProperties adminServerProperties) {
		this.adminContextPath = adminServerProperties.getContextPath();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
		successHandler.setTargetUrlParameter("redirectTo");
		successHandler.setDefaultTargetUrl(adminContextPath + "/");
		// admin监控 用户鉴权
		httpSecurity.authorizeRequests()
			//授予对所有静态资产和登录页面的公共访问权限。
			.antMatchers(adminContextPath + "/assets/**").permitAll()
			.antMatchers(adminContextPath + "/login").permitAll()
			//必须对每个其他请求进行身份验证
			.anyRequest().authenticated().and()
			//配置登录和注销
			.formLogin().loginPage(adminContextPath + "/login")
			.successHandler(successHandler).and()
			.logout().logoutUrl(adminContextPath + "/logout").and()
			//启用HTTP-Basic支持。这是Spring Boot Admin Client注册所必需的
			.httpBasic().and().csrf().disable()
			.headers().frameOptions().disable();
	}

	@Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        JdbcDaoImpl service = new JdbcDaoImpl();
        service.setDataSource(dataSource);
        return service;
    }

    /**
     * 强散列哈希加密实现
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

}
