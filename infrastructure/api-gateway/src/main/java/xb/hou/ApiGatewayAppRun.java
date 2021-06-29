package xb.hou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @title: ApiGatewayAppRun
 * @Author xbhou
 * @Date: 2021-06-27 21:28
 * @Version 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayAppRun {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayAppRun.class, args);
    }
}
