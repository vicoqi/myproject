package com.vic.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 46. 全排列
 * 给定一个 没有重复 数字的序列，返回其所有可能的全排列。
 * 示例:
 *
 * 输入: [1,2,3]
 * 输出:
 * [
 *   [1,2,3],
 *   [1,3,2],
 *   [2,1,3],
 *   [2,3,1],
 *   [3,1,2],
 *   [3,2,1]
 * ]
 *
 * @author: wangqp
 * @create: 2020-09-07 23:56
 */
public class PM46 {
    public List<List<Integer>> permute(int[] nums) {
        boolean[] used = new boolean[nums.length];
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();
        back(nums,used,temp,result);
        return result;
    }
    private void back(int[] nums, boolean[] used, List<Integer> temp,List<List<Integer>> result){
        if (temp.size() == nums.length){
            result.add(new ArrayList<>(temp));
            return;
        }
        for (int i = 0; i < used.length; i++) {
            if (!used[i]){
                used[i] = true;
                temp.add(nums[i]);

                back(nums,used,temp,result);

                used[i] = false;
                temp.remove(temp.size()-1);
            }
        }
    }
}
