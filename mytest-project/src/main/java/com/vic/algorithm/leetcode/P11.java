package com.vic.algorithm.leetcode;

/**
 * 11. 盛最多水的容器
 *
 * 示例 2：
 * 输入：height = [1,1]
 * 输出：1
 *
 * 示例 3：
 * 输入：height = [4,3,2,1,4]
 * 输出：16
 *
 * 示例 4：
 * 输入：height = [1,2,1]
 * 输出：2
 *
 * @author: wangqp
 * @create: 2020-11-12 17:34
 */

public class P11 {

    public int maxArea(int[] height) {
        int head = 0;
        int tail = height.length-1;
        int max = 0;
        while (head <= tail){
            if (height[head] < height[tail]){
                max = Math.max(Math.abs((height[head]) * (head - tail)),max);
                head++;
            }else {
                max = Math.max(Math.abs((height[tail]) * (head - tail)),max);
                tail--;
            }
        }
        return max;
    }

    /**
     * 思路 短板效应
     * 双指针，每次最小值的指针，往前移动
     */
}
