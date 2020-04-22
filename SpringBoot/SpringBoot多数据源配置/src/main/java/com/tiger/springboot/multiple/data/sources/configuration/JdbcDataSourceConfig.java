package com.tiger.springboot.multiple.data.sources.configuration;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class JdbcDataSourceConfig {

    /****** 数据源1 ******/
    //存在多个时，需要指定唯一一个主要的实现，
    //@Primary //或者直接在启动类中排除掉默认的实现
    @Bean
    @ConfigurationProperties(prefix = "app.datasource.db1")
    public DataSourceProperties db1DataSourceProperties() {
        return new DataSourceProperties();
    }

    //存在多个时，需要指定唯一一个主要的实现，
    //@Primary //或者直接在启动类中排除掉默认的实现
    @Bean
    public DataSource db1DataSource() {
        return db1DataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "db1JdbcTemplate")
    public JdbcTemplate db1JdbcTemplate() {
        return new JdbcTemplate(db1DataSource());
    }

    /****** 数据源2 ******/
    @Bean
    @ConfigurationProperties(prefix = "app.datasource.db2")
    public DataSourceProperties db2DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource db2DataSource() {
        return db2DataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "db2JdbcTemplate")
    public JdbcTemplate db2JdbcTemplate() {
        return new JdbcTemplate(db2DataSource());
    }

}
