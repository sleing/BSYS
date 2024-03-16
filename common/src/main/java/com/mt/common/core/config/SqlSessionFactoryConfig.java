package com.mt.common.core.config;

import com.github.abel533.sql.SqlMapper;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class SqlSessionFactoryConfig {
    @Autowired
    DataSource dataSource;

    @Bean
    @Primary
    public SqlMapper sqlMapper() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);//更多参数请自行注入
        SqlSession session = bean.getObject().openSession();
        SqlMapper sqlMapper = new SqlMapper(session);

        return sqlMapper;
    }
}
