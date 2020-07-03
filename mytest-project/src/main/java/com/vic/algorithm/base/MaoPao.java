package com.vic.algorithm.base;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author: wangqp
 * @create: 2020-07-02 16:11
 */
public class MaoPao {

    @Test
    public void maopao(){
        Integer[] a = new Integer[]{3,6,1,8,2,0};
        for (int i=1;i<a.length;i++){
            for (int j=0;j<a.length-i;j++){
                if(a[j] < a[j+1]){
                    int temp = a[j];
                    a[j]  =  a[j+1];
                    a[j+1] = temp;
                }
            }
        }
        System.out.print(Arrays.toString(a));
    }
}

