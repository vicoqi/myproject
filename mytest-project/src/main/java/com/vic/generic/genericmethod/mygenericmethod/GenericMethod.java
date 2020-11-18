package com.vic.generic.genericmethod.mygenericmethod;

import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @description:
 * @author: wangqp
 * @create: 2020-06-19 16:25
 **/
public class GenericMethod{

    public <T extends ISigin> T c( T t) {
        Method method = Arrays.stream(t.getClass().getMethods()).filter(m-> "sigin".equals(m.getName())).findFirst().get();
        Arrays.stream(method.getParameterAnnotations()[0]).forEach(a-> System.out.println(a.toString()));
        Arrays.stream(method.getAnnotations()).forEach(a-> System.out.println(a.toString()));

        MethodIntrospector.selectMethods(t.getClass(),
                (MethodIntrospector.MetadataLookup<MyMethodAnnotation>) (methodx) -> {
                    return (MyMethodAnnotation) AnnotatedElementUtils.findMergedAnnotation(methodx, MyMethodAnnotation.class);
                });

        return t;
    }


    //https://www.cnblogs.com/wwjj4811/p/12592443.html
//    @Test  //获取方法的参数中的泛型信息
//    public void test() throws NoSuchMethodException {
//        Method method = Test02.class.getMethod("parameterTest", Map.class);
//        //获取方法的参数类型
//        Type[] genericParameterTypes = method.getGenericParameterTypes();
//        for (Type genericParameterType : genericParameterTypes) {
//            System.out.println("type:"+genericParameterType);
//            //ParameterizedType:表示一种参数化类型，比如Collection<Object>
//            if(genericParameterType instanceof ParameterizedType){
//                Type[] actualTypeArguments = ((ParameterizedType) genericParameterType).getActualTypeArguments();
//                for (Type parameterType : actualTypeArguments) {
//                    System.out.println(parameterType);
//                }
//            }
//        }
//    }
}
