package com.vic.algorithm.base;

import org.junit.Test;

import java.util.Arrays;

/**
 * 归并排序，分治的思想
 * 该算法是采用分治法（Divide and Conquer）的一个非常典型的应用。
 * https://blog.csdn.net/daigualu/article/details/78399168
 * 基本思想是，两个有序的东西，归一，merge。
 * 但是需要先，把东西都分开，排序，然后归一。
 * @author: wangqp
 * @create: 2020-07-08 17:32
 */
public class MergeSort {

    public void sort(int[] arrs,int a,int b){
        if(a>=b){
            return;
        }
        int m = (a+b)/2;

        sort(arrs,a,m);
        sort(arrs,m+1,b);
        merge(arrs,a,m,b);
    }

    //1,3,2,4   0,1(2),3
    public static void merge(int[] arrs,int i,int m,int j){
        int[] temp = new int[j-i+1];
        int x = m+1;
        while(i<=m && x<=j){
            if(arrs[i]>arrs[x]){
                temp[i+x-m-1] = arrs[x];
                x++;
            }else {
                temp[i+x-m-1] = arrs[i];
                i++;
            }
        }
        //其中一个序列处理到头，把另一个处理下
        while (i<=m){
            temp[i+x-m-1] = arrs[i];
            i++;
        }
        while (x<=j){
            temp[i+x-m-1] = arrs[x];
            x++;
        }
        System.out.println(Arrays.toString(temp));
        for(int a = 0;i+a<=j;a++){
            arrs[i+a] = temp[a];
        }
    }

    public static void main(String[] args) {
        int[] arrs = {2,3,0,1,4,7};
        merge(arrs,0,1,5);
    }
}
