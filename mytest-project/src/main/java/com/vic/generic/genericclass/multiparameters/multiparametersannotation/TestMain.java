package com.vic.generic.genericclass.multiparameters.multiparametersannotation;

import org.apache.commons.lang3.reflect.TypeUtils;
import org.junit.Test;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Map;

/**
 * @author: wangqp
 * @create: 2020-11-17 17:19
 */
public class TestMain {

    @Test
    public void testGenericSuperclass(){
        Class<MultiParamsImpl> multiParamsClass = MultiParamsImpl.class;

        // getGenericSuperclass() 得到父类（不是接口类）的泛型
        Type t = multiParamsClass.getGenericSuperclass();

        //type 进行强转，转换成能获取到参数的 代表 当前类的类
        ParameterizedType p = (ParameterizedType) t;

        //类上的泛型
        Type[] a = p.getActualTypeArguments();

        System.out.println(a[0].getTypeName());
        System.out.println( ((Class)a[0]).getSimpleName());
    }


    //单一接口，得不到接口的父接口
    @Test
    public void testGenericInterfaceclass(){
        Class<MultiParamsImpl> multiParamsClass = MultiParamsImpl.class;

        // getGenericInterfaces() 得到 所有 接口类
        //不确定  接口 上 还有父接口
        Type[] types = multiParamsClass.getGenericInterfaces();
        for (Type t:types) {
            //type 进行强转，转换成能获取到参数的 代表 当前类的类
            ParameterizedType p = (ParameterizedType) t;
            //类上的泛型
            Type[] arguments = p.getActualTypeArguments();

            for (Type argument:arguments) {
                System.out.println(argument.getTypeName());
                System.out.println(((Class) argument).getSimpleName());
            }
        }
    }

    //接口还有父接口，通过验证只能先得到子接口，然后去得到父接口
    @Test
    public void testGenericInterfaceWithSuperInterface(){
        Class<MultiParamsImpl> multiParamsClass = MultiParamsImpl.class;

        // getGenericInterfaces() 得到 所有 接口类
        //不确定  接口 上 还有父接口
        Type[] types = multiParamsClass.getGenericInterfaces();
        for (Type t:types) {
            //type 进行强转，转换成能获取到参数的 代表 当前类的类
            ParameterizedType p = (ParameterizedType) t;
            //得到当前接口 有泛型的 所有父接口
            Type[] parentTypes = ((Class) p.getRawType()).getGenericInterfaces();

            for (Type pt:parentTypes) {

                ParameterizedType ppt = (ParameterizedType) pt;

                //类上的泛型
                Type[] parentArguments = ppt.getActualTypeArguments();

                for (Type parentArgument:parentArguments) {
                    System.out.println("父接口： "+parentArgument.getTypeName());
                    System.out.println("父接口： "+((Class) parentArgument).getSimpleName());
                }
            }

            //类上的泛型
            Type[] arguments = p.getActualTypeArguments();

            for (Type argument:arguments) {
                System.out.println(argument.getTypeName());
                System.out.println(((Class) argument).getSimpleName());
            }
        }
    }
    
    //单一接口 接口泛型被 注解 标定
    @Test
    public void testGenericInterfaceWithNote(){
        Class<MultiParamsImpl> multiParamsClass = MultiParamsImpl.class;

        // getGenericInterfaces() 得到 所有 接口类
        //不确定  接口 上 还有父接口
        Type[] types = multiParamsClass.getGenericInterfaces();
        for (Type t:types) {
            
            //得到这个接口上的 所有泛型
//            Map<TypeVariable<?>, Type> typeArguments = TypeUtils.getTypeArguments(multiParamsClass, IMultiParamInterface.class);
//            for (Map.Entry<TypeVariable<?>, Type> typeVariableTypeEntry : typeArguments.entrySet()) {
//                TypeVariable<?> key = typeVariableTypeEntry.getKey();
//                MyAxis annotation1 = key.getAnnotation(MyAxis.class);
//                MyAxis annotation = AnnotationUtils.getAnnotation(key, MyAxis.class);
//                Type value = typeVariableTypeEntry.getValue();
//            }

            //type 进行强转，转换成能获取到参数的 代表 当前类的类
            ParameterizedType p = (ParameterizedType) t;
            TypeVariable[] typeParameters = ((Class) p.getRawType()).getTypeParameters();
            MyAxis annotation1 = typeParameters[0].getAnnotation(MyAxis.class);

            //类上的泛型
            Type[] arguments = p.getActualTypeArguments();

            for (Type argument:arguments) {
                System.out.println(argument.getTypeName());

                Annotation annotation = ((TypeVariable) argument).getAnnotation(MyAxis.class);
                System.out.println(((Class) argument).getSimpleName());

            }
        }
    }

    //方法上有泛型，怎么办？

}
