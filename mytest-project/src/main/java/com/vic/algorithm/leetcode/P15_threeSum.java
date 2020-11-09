package com.vic.algorithm.leetcode;

import java.util.*;

/**
 * 15. 三数之和
 * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有满足条件且不重复的三元组。
 *
 * 注意：答案中不可以包含重复的三元组。
 *
 * 示例：
 *
 * 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
 *
 * 满足要求的三元组集合为：
 * [
 *   [-1, 0, 1],
 *   [-1, -1, 2]
 * ]
 *
 * 链接：https://leetcode-cn.com/problems/3sum
 * @author: wangqp
 * @create: 2020-09-16 14:25
 */
public class P15_threeSum {

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ret = new LinkedList<>();
        //注册下 , key (v1+v2),  index[]
        Map<Integer,List<List<Integer>>> map = new HashMap<>();

        //注册成一个 key index; 单值 做成 hash ,与上面的双值组成的 hash 匹配
        Map<Integer,Integer> danValueMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            danValueMap.put(nums[i],i);
        }

        for (int i = 0; i < nums.length; i++) {
            for (int j = i+1; j < nums.length; j++) {
                List<List<Integer>> list = map.get(nums[i]+nums[j]);
                if (list == null){
                    list = new ArrayList<>();
                    map.put(nums[i]+nums[j],list);
                }
                //感觉需要去重
                for (int k = 0; k < list.size(); k++) {
                    if (list.get(0).contains(nums[j]));
                }
                List<Integer> sub = new ArrayList<>();
                sub.add(nums[i]);
                sub.add(nums[j]);
                list.add(sub);
            }
        }

        //找到注册的信息
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(-nums[i])){
                for (int j = 0; j < map.get(-nums[i]).size(); j++) {
                    List<Integer> integers = new ArrayList<>(map.get(-nums[i]).get(j));
                    //不能包含自己位置的索引，即不能用重复元素 去组成零。
                    if (map.get(-nums[i]).get(j).contains(i)){
                        continue;
                    }
                    List<Integer> subRet = new ArrayList<>();
                    subRet.add(nums[i]);
                    subRet.add(nums[integers.get(0)]);
                    subRet.add(nums[integers.get(1)]);
                    ret.add(subRet);
                }
                map.remove(-nums[i]);
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        int[] nums = {-1,0,1,2,-1,-4};
        new P15_threeSum().threeSum(nums);
    }
}
