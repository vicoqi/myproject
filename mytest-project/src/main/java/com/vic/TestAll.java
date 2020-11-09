package com.vic;

import lombok.AllArgsConstructor;
import lombok.ToString;
import org.junit.Test;

import java.util.*;

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

/*3
        TestAll a = new TestAll();
        System.out.println(a.allDriveunitReported);
        */
/* 4
        String serverBucketWaypointID = null;
        String aa = String.format( "%s",serverBucketWaypointID);
        System.out.println(aa);
        */
/*5
    new TestAll().ss();
    */
//        double agvMaxStopSpeed = 1.0e-3;
//        System.out.println(String.valueOf(agvMaxStopSpeed));

//        int x = (int) (12500 / 1000 + 0.5);
//        int y = (int) (17500 / 1000 + 0.5);
//        System.out.println(""+x+"|||"+y);
        try {
            new  TestAll().sas();
        }catch (Exception e){
            System.out.println("1-=-=-=-qw134");
        }
    }

    public void ss(){
        try{
            AA a = null;
            String jobId = Optional.ofNullable(a).map(AA::getAa).orElseThrow(
                    () -> new Exception("aa"));
            System.out.println(jobId);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void sas() throws Exception {
        try{
            int a = 1/0;
        }catch (Exception e){
            System.out.println("1-=-=-=-");
            throw new Exception();
        }finally {
            System.out.println("111212");
        }
    }

    public class AA{
        String aa,bb,cc;
        public String getAa(){
            return aa;
        }
    }

    @Test
    public void testMap(){
        Map<AB,String> map = new HashMap<>();
        map.put(new AB(1, Collections.singletonList("1")),"1");

        System.out.println(map.get(new AB(1, Collections.singletonList("1"))));
    }

    @Test
    public void sleep(){
        while (true){
            System.out.println("ready to sleep");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    @AllArgsConstructor
    class AB{
        int a;
        List<String> b;
    }
}
