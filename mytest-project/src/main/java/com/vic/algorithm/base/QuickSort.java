package com.vic.algorithm.base;

import java.util.Arrays;
/**
 * 思想 @see https://blog.csdn.net/u014241071/article/details/81565148
 * 标准做法 @see https://blog.csdn.net/Luyanc/article/details/93521378
 * @author: wangqp
 * @create: 2020-07-03 15:18
 */
public class QuickSort {
    public static void main(String[] args){
        Integer[] arr = {5,2,7,3,9,10,8,6,1,4};
        quickSort(arr,0,arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

    //排序方法-假设从小到大排序
    public static void quickSort(Integer[] arr,int low,int high){
        if(low < high){
            int part=partition(arr,low,high);
            //递归调用
            quickSort(arr,low,part-1);
            quickSort(arr,part+1,high);
        }
    }

    //划分方法
    private static int partition(Integer[] arr,int low,int high){
        //使用 r[low]作为枢轴元素
        int pivot = arr[low];
        //从两端交替向内扫描
        while(low < high){
            while(low<high && arr[high] >= pivot) {high--;}
            //将比 pivot 小的元素移向低端
            arr[low] = arr[high];
            while(low<high && arr[low] <= pivot){low++;}
            //将比 pivot 大的元素移向高端
            arr[high] = arr[low];
        }
        //设置枢轴
        arr[low]=pivot;
        //返回枢轴元素位置
        return low;
    }
}
