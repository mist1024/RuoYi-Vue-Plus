package com.jiajiakang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 启动程序
 * 
 * @author ruoyi
 */
@EnableFeignClients
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class JiaJiaKangApplication
{
    public static void main(String[] args)
    {
        System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(JiaJiaKangApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  ~~  佳家康管理系统启动成功   ~~   ლ(´ڡ`ლ)ﾞ");
    }
}
