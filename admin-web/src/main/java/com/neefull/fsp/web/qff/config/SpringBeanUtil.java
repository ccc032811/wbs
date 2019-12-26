package com.neefull.fsp.web.qff.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author: chengchengchu
 * @Date: 2019/12/13  19:31
 */

@Component
public class SpringBeanUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    public static <T> T getObject(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }


    public static void showClass() {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }

    }
}
