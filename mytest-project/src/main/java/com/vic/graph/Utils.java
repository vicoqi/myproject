package com.vic.graph;

/**
 * @Auther: wqp
 * @Date: 2018/12/13 16:34
 * @Description:
 */
/**
 * 一些工具类
 */
public class Utils {
    /**
     * 打印信息
     * @param t
     */
    public static void log(Object t) {
        System.out.println(t);
    }

    /**
     * 打印信息
     * @param
     */
    public static void log(String format, Object... args) {
        String str = String.format(format, args);
        System.out.println(str);
    }
}