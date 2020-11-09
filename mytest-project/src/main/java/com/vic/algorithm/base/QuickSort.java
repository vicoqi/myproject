package com.vic.algorithm.base;

import java.util.Arrays;
/**
 * 快排的时间复杂度nlogn
 * 思想 @see https://blog.csdn.net/u014241071/article/details/81565148
 * 标准做法 @see https://blog.csdn.net/Luyanc/article/details/93521378
 * @author: wangqp
 * @create: 2020-07-03 15:18
 */
public class QuickSort {
    public static void main(String[] args){
        Integer[] arr = {5,2,7,3,9,10,8,6,1,4};
        quickSort1(arr,0,arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

    private static void quickSort1(Integer[] arr, int i, int j) {
        if(i<j){
            int m = partition1(arr,i,j);
            quickSort1(arr,i,m-1);
            quickSort1(arr,m+1,j);
        }
    }
    private static int partition1(Integer[] arr, int i, int j) {
        int bijiaozhi = arr[i];
        while (i<j){
            while (i<j && bijiaozhi <= arr[j]) {
                j--;
            }
            arr[i] = arr[j];
            while (i<j && bijiaozhi >= arr[i]) {
                i++;
            }
            arr[j] = arr[i];
        }
        //这时 i==j 了
        arr[j] = bijiaozhi;
        return j;
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
        //这时 low==high 了
        //设置枢轴
        arr[low]=pivot;
        //返回枢轴元素位置
        return low;
    }
}
