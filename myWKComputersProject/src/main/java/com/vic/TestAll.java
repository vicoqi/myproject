package com.vic;

/**
 * @Date: 2018/8/29 14:02
 * @Description:
 */
public class TestAll {
    private volatile boolean allDriveunitReported;  //初始化 默认是 false

    public static void main(String[] args) {

//  1.      double[][] vv = new double[0][];

/*  2.
        String aa = "851979|917515|983051|1048587";
        String[] as = aa.trim().split("\\|");
        for(String a:as){
            System.out.println(a);
        }
        */


        TestAll a = new TestAll();
        System.out.println(a.allDriveunitReported);
    }
}
