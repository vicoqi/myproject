package com.vic.area;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: wqp
 * @Date: 2019/3/2 17:10
 * @Description:
 * 离散区域 -- 算法
 */
public class DiscreteRegion {
    public static void main(String[] args) {
        List<XY> lt = new ArrayList<>();
        lt.add(new XY(11000,11000));
        lt.add(new XY(12000,12000));
        lt.add(new XY(13000,13000));
//        lt.add(new XY(4000,4000));
        lt.add(new XY(15000,15000));
//        lt.add(new XY(11000,11000));
//        lt.add(new XY(10000,10000));
        //方差
        int variance = aa(lt.parallelStream().map(XY::getX).collect(Collectors.toList()), (int) lt.stream().mapToInt(o->o.getX()).average().getAsDouble());
        System.out.println(variance);
        double biaozhuncha = bb(lt.parallelStream().map(XY::getX).collect(Collectors.toList()), (int) lt.stream().mapToInt(o->o.getX()).average().getAsDouble());
        System.out.println("标准差:"+biaozhuncha);
    }

    public static int aa(List<Integer> co,int p){
        int temp = 0;
        for(int i=0;i<co.size();i++){
            temp += Math.pow(co.get(i)-p,2);
        }
        return temp/co.size();
    }

    //标准差
    public static double bb(List<Integer> co, int p){
        Double temp = 0D;
        for(int i=0;i<co.size();i++){
            temp += Math.pow(co.get(i)-p,2);
        }
        return Math.sqrt(temp/co.size());
    }

    @AllArgsConstructor
    @Data
    static class XY{
        int x;
        int y;
    }
}
