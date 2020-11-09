package com.vic.java_deep.reflex;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: wangqp
 * @create: 2020-07-13 15:26
 */
public class MapMain {
    private static final int COUNT = 100000;

    public static void main(String[] args) {
//        System.out.println("starting ...");
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < COUNT; i++) {
//            ClassA clzA = new ClassA();
//            clzA.setName("A" + i);
//            clzA.setAge(i);
//            clzA.setAddress("西安市雁塔区");
//            clzA.addProjectExperience("持续集成产品", "agent开发");
//            clzA.addProjectExperience("持续部署产品", "引擎开发");
//            clzA.addProjectExperience("云平台", "新的平台规划主要功能开发");
//        }
//        long end = System.currentTimeMillis();
//        long diff = end - start;
//        System.out.println("native call:" + diff);
//
//        try {
//            long start2 = System.currentTimeMillis();
//            for (int i = 0; i < COUNT; i++) {
//                Class clzA = Class.forName("com.yq.myreflect.ClassA");
//                Class<?>[] argsType = new Class[1];
//                argsType[0] = String.class;
//
//                Class<?>[] argsTypeInt = new Class[1];
//                argsTypeInt[0] = Integer.class;
//
//                Class<?>[] argsTypeMap = new Class[2];
//                argsTypeMap[0] = String.class;
//                argsTypeMap[1] = String.class;
//
//                Method setName = clzA.getMethod("setName", argsType);
//                Method setAge = clzA.getMethod("setAge", argsTypeInt);
//                Method setAddress = clzA.getMethod("setAddress", argsType);
//                Method addProjectExperience = clzA.getMethod("addProjectExperience", argsTypeMap);
//
//                Object obj = clzA.newInstance();
//                setName.invoke(obj, "A" + i);
//                setAge.invoke(obj,  i);
//                setAddress.invoke(obj, "西安市雁塔区");
//                addProjectExperience.invoke(obj, "持续集成产品", "agent开发");
//                addProjectExperience.invoke(obj, "持续部署产品", "引擎开发");
//                addProjectExperience.invoke(obj, "云平台", "新的平台规划主要功能开发");
//            }
//            long end2 = System.currentTimeMillis();
//            long diff2 = end2 - start2;
//            System.out.println("reflect call:" + diff2);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }


        try {
            long start3 = System.currentTimeMillis();

//            Class clzA = Class.forName("com.vic.java_deep.reflex.ClassA");
            Class clzA = ClassA.class;
            Class<?>[] argsType = new Class[1];
            argsType[0] = String.class;

            Class<?>[] argsTypeInt = new Class[1];
            argsTypeInt[0] = Integer.class;

            Class<?>[] argsTypeMap = new Class[2];
            argsTypeMap[0] = String.class;
            argsTypeMap[1] = String.class;

            Method setName = clzA.getMethod("setName", argsType);
            Method setAge = clzA.getMethod("setAge", argsTypeInt);
            Method setAddress = clzA.getMethod("setAddress", argsType);
            Method addProjectExperience = clzA.getMethod("addProjectExperience", argsTypeMap);

            for (int i = 0; i < COUNT; i++) {
                Object obj = clzA.newInstance();
                setName.invoke(obj, "A" + i);
                setAge.invoke(obj,  i);
                setAddress.invoke(obj, "西安市雁塔区");
                addProjectExperience.invoke(obj, "持续集成产品", "agent开发");
                addProjectExperience.invoke(obj, "持续部署产品", "引擎开发");
                addProjectExperience.invoke(obj, "云平台", "新的平台规划主要功能开发");
            }
            long end3 = System.currentTimeMillis();
            long diff3 = end3 - start3;
            System.out.println("cache call:" + diff3);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Test
    public void testFieldCost() throws IllegalAccessException {
        ClassA clzA = new ClassA();
        clzA.setName("A" + 1);
        clzA.setAge(1);
        clzA.setAddress("西安市雁塔区");
        clzA.addProjectExperience("持续集成产品", "agent开发");
        clzA.addProjectExperience("持续部署产品", "引擎开发");
        clzA.addProjectExperience("云平台", "新的平台规划主要功能开发");

        Field[] declaredFields = ClassA.class.getDeclaredFields();

        long start4 = System.currentTimeMillis();

        for (int i = 0; i < COUNT; i++) {
            Map<String, Object> map = new LinkedHashMap<>();
            for (Field f : declaredFields) {
                f.setAccessible(true);
                map.put(f.getName(), f.get(clzA));
            }
        }
        System.out.println("cache call:" + (System.currentTimeMillis()-start4));
    }
}


