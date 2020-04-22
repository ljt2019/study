package com.tiger.starter.configuration;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * 关联配置文件类
 */
@ConfigurationProperties(prefix = TigerProperties.TIGER_FORMAT_PREFIX)
public class TigerProperties {

    public static final String              TIGER_FORMAT_PREFIX = "tiger.format";
    private             Map<String, Object> info;
    private             String              name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getInfo() {
        return info;
    }

    public void setInfo(Map<String, Object> info) {
        this.info = info;
    }
}
