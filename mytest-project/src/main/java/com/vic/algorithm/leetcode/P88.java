package com.vic.algorithm.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 88. 合并两个有序数组
 *
 * 给你两个有序整数数组 nums1 和 nums2，请你将 nums2 合并到 nums1 中，使 nums1 成为一个有序数组。
 *
 * 输入:
 * nums1 = [1,2,3,0,0,0], m = 3
 * nums2 = [2,5,6],       n = 3
 *
 * 输出: [1,2,2,3,5,6]
 *
 * 说明:
 * 初始化 nums1 和 nums2 的元素数量分别为 m 和 n 。
 * 你可以假设 nums1 有足够的空间（空间大小大于或等于 m + n）来保存 nums2 中的元素。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/merge-sorted-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author: wangqp
 * @create: 2020-08-13 16:15
 */
public class P88 {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int[] newN = new int[m];
        System.arraycopy(nums1,0,newN,0,m);
        int i=0,j=0;
        for (;i<m || j<n;){
            if (i>=m){
                nums1[i+j] = nums2[j];
                j++;
            }
            if (j>= n){
                nums1[i+j] = newN[i];
                i++;
            }
            if (newN[i] <= nums2[j]){
                nums1[i+j] = newN[i];
                i++;
            }else {
                nums1[i+j] = newN[j];
                j++;
            }
        }
    }

    @Test
    public void Copy(){
        int [] num1 = {1,2,0,0};
        int [] num2 = {3,4};
        System.arraycopy(num2, 0, num1, 2, 2);
        System.out.println(Arrays.toString(num1));
    }
}
