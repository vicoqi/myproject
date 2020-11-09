package com.vic.algorithm.leetcode;

/**
 * 53. 最大子序和
 * [-1,2,1,1,-1,3,8]
 * [-1,2,1,1,-9,3,8]
 *
 * [1,2,5,-1,9]
 *
 * 输入: [-2,1,-3,4,-1,2,1,-5,4]
 * 输出: 6
 * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
 *
 * 链接：https://leetcode-cn.com/problems/maximum-subarray/solution/hua-jie-suan-fa-53-zui-da-zi-xu-he-by-guanpengchn/
 * @author: wangqp
 * @create: 2020-08-07 11:40
 */
public class MaxSubArray {

    //         以 num[i] 结尾
    // 这个才是 标准动态规划 dp[i] 定义为数组nums ，以 num[i] 结尾的最大连续子串和， 则有dp[i] = max(dp[i-1] + nums[i], num[i]);
    public int DynamicMaxSubArray(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(dp[i- 1] + nums[i], nums[i]);
            if (max < dp[i]) {
                max = dp[i];
            }
        }
        return max;
    }

    public int maxSubArray(int[] nums) {
        int sum = 0;
        int max = nums[0];
        for (int i=0;i<nums.length;i++){
            if (sum > 0){
                sum+=nums[i];
            }else {
                sum = nums[i];
            }
            max = Math.max(sum,max);
        }
        return max;
    }
}
