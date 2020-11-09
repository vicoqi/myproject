package com.vic.algorithm.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/two-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * 给定 nums = [2, 7, 11, 15], target = 9
 *
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 *
 * @author: wangqp
 * @create: 2020-08-10 17:37
 */
public class TwoSum {
    public int[] twoSum(int[] nums, int target) {
        //登记的值 -》 下标索引
        Map<Integer,Integer> dengji = new HashMap<>();
        //没找到匹配 就登记下，让别人来找
        for (int i = 0; i < nums.length; i++) {
            if (dengji.containsKey(target-nums[i])){
                return new int[]{i,dengji.get(target-nums[i])};
            }
            dengji.put(nums[i],i);
        }
        return null;
    }
}
