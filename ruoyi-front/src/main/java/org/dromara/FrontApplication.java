package org.dromara;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

@SpringBootApplication
public class FrontApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(FrontApplication.class);
        application.setApplicationStartup(new BufferingApplicationStartup(2048));
        application.run(args);
        System.out.println("(♥◠‿◠)ﾉﾞ  RuoYi-Front启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
