package com.tiger.starter.configuration;


import com.tiger.starter.TigerFormatTemplate;
import com.tiger.starter.format.FormatProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Bean 配置类
 */
@Import(FormatConditionalAutoConfiguration.class)//导入模板配置类
@EnableConfigurationProperties(TigerProperties.class)//自动注入配置信息
@Configuration//标识是一个配置类
public class TigerBeanAutoConfiguration {

    @Bean
    public TigerFormatTemplate tigerFormatTemplate(TigerProperties tigerProperties, FormatProcessor formatProcessor) {
        return new TigerFormatTemplate(tigerProperties, formatProcessor);
    }
}
