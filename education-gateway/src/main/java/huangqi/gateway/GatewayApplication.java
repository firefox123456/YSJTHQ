package huangqi.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 网关启动类
 *
 * @author "黄骐"
 * @date 2023/08/20 14:34
 **/
@SpringBootApplication
@EnableDiscoveryClient
@RefreshScope
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
