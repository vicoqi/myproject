package com.vic.spring.beanfactoryprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.beans.Introspector;

/**
 * @description:
 * @author: wangqp
 * @create: 2020-06-10 23:59
 **/
@Component
public class TestBeanFactoryProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        RootBeanDefinition definition = new RootBeanDefinition(
                NoComponentTest.class);

        System.out.println(NoComponentTest.class.getName());

        System.out.println(NoComponentTest.class.getSimpleName());

        System.out.println(NoComponentTest.class.getCanonicalName());

        System.out.println(BeanDefinitionReaderUtils.generateBeanName(definition, registry));

        String shortClassName = ClassUtils.getShortName(NoComponentTest.class.getName());
        String diBeanName =  Introspector.decapitalize(shortClassName);
        System.out.println(diBeanName);

        registry.registerBeanDefinition(diBeanName, definition);
    }


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
