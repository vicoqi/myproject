package com.vic.spring.imports;

/**
 * Created by wang on 2019/6/28.
 */
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class SpringStudySelector implements ImportSelector, BeanFactoryAware {
    private BeanFactory beanFactory;

    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        for(String s:importingClassMetadata.getAnnotationTypes()){
            System.out.println(s);
        }
        System.out.println(beanFactory);
        return new String[]{StudentBean.class.getName()};
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
