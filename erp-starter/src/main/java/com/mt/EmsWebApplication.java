package com.mt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

//import org.springframework.context.annotation.ComponentScan;

@EnableAsync
@EnableCaching
//@MapperScan({"com.mt.**.mapper","com.mt.**.dao"})
@MapperScan({"com.mt.**.mapper","com.mt.**.dao"})
//@ComponentScan(basePackages = {"com.mt.common","com.mt.erp", "com.mt.lis", "com.ddd", "org.jeecg.modules.jmreport"})
@ComponentScan(basePackages = {"com.mt.*","com.mt.bms","com.mt.tms","com.mt.common","com.mt.ems", "com.ddd.dev.generator","org.jeecg.modules.jmreport"})
@SpringBootApplication()
public class EmsWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmsWebApplication.class, args);
    }

}
