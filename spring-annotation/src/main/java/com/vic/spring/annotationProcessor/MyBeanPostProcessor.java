package com.vic.spring.annotationProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("postProcessBeforeInitialization..."+beanName+"=>"+bean);
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		Map annotatedMethods = null;
		try {
			annotatedMethods = MethodIntrospector.selectMethods(bean.getClass(), (MethodIntrospector.MetadataLookup<MyEventListener>) (methodx) -> {
				return AnnotatedElementUtils.findMergedAnnotation(methodx, MyEventListener.class);
			});
			if(!CollectionUtils.isEmpty(annotatedMethods)){
				for(Map.Entry<Method, MyEventListener> entry:(Set<Map.Entry<Method, MyEventListener>>)annotatedMethods.entrySet()){
					Class p = entry.getKey().getParameterTypes()[0];
					String order = entry.getValue().order();
				}
			}
		} catch (Throwable var12) {
		}
		return bean;
	}

}
