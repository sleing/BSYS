package com;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 *
 * @author CISDI
 * @date 2018/04/27
 */
@SpringBootApplication(scanBasePackages = {"com.ddd.info.dcm.*","com.ddd.info.simple.*","com.ddd.info.rabbit.*","com.ddd.dev.*","com.ddd.sms.*"}, exclude = {SecurityAutoConfiguration.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
