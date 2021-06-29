package xb.hou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @title: SystemAppRun
 * @Author xbhou
 * @Date: 2021-06-27 21:26
 * @Version 1.0
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableDiscoveryClient
public class SystemAppRun {
    public static void main(String[] args) {
        SpringApplication.run(SystemAppRun.class, args);
    }
}
