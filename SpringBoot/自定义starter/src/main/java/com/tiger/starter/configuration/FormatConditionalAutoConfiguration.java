package com.tiger.starter.configuration;


import com.tiger.starter.format.FormatProcessor;
import com.tiger.starter.format.JsonFormatProcessor;
import com.tiger.starter.format.StringFormatProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 条件注入
 */
@Configuration
public class FormatConditionalAutoConfiguration {

    /**
     * 不存在【com.alibaba.fastjson.JSON】时 ，注入
     *
     * @return
     */
    @ConditionalOnMissingClass("com.alibaba.fastjson.JSON")
    @Bean
    @Primary //主要的
    public FormatProcessor stringFormat() {
        return new StringFormatProcessor();
    }

    /**
     * 存在【com.alibaba.fastjson.JSON】时注入
     *
     * @return
     */
    @ConditionalOnClass(name = "com.alibaba.fastjson.JSON")
    @Bean
    public FormatProcessor jsonFormat() {
        return new JsonFormatProcessor();
    }

}
