package com.tiger;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import java.util.Objects;

/**
 * @description: 非spring管理Bean获得spring管理Bean工具类
    @Autowired
    private static RestTemplate restTemplate = SpringUtil.getBean(RestTemplate.class);
 * @author: tiger
 * @create: 2020-04-14 22:27
 */
public abstract class SpringUtil {
	
    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (Objects.nonNull(SpringUtil.applicationContext)) {
            throw new RuntimeException("applicationContext不可动态更改");
        }
        SpringUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static boolean contains(String beanName) {
        return applicationContext.containsBean(beanName);
    }

    public static <T> T getBean(Class<T> requireType) {
        return applicationContext.getBean(requireType);
    }

    public static <T> T getBean(String beandId, Class<T> requireType) {
        return applicationContext.getBean(beandId, requireType);
    }
}
