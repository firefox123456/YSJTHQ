package huangqi.teacher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 讲师管理启动类
 *
 * @author "黄骐"
 * @date 2023/08/19 20:23
 **/

@SpringBootApplication
@EnableDiscoveryClient
//刷新配置
@RefreshScope
public class TeacherApplication {
    public static void main(String[] args) {
        SpringApplication.run(TeacherApplication.class,args);
    }
}
