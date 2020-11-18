package com.vic.algorithm.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * 3. 无重复字符的最长子串
 *
 * 剑指 Offer 48. 最长不含重复字符的子字符串
 *
 * 示例 1:
 *
 * 输入: "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * 示例 2:
 *
 * 输入: "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 * 示例 3:
 *
 * 输入: "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 *
 */

public class lengthOfLongestSubstring {

    public int MyLengthOfLongestSubstring(String s) {
        Set<Character> set = new HashSet<>();
        int maxLength = 0;
        int head = 0;
        int tail = 0;
        while (tail <= head && head < s.length()){
            if (!set.contains(s.charAt(head))){
                set.add(s.charAt(head));
                head++;
                maxLength = Math.max(maxLength,head-tail);
            }else {
                set.remove(s.charAt(tail));
                tail++;
            }
        }
        return maxLength;
    }



    //正解，好理解的方式，滑动窗口，双指针。
    public int lengthOfLongestSubstring(String s) {
        Set<Character> set = new HashSet<>();
        int left = 0, right = 0, res = 0;
        while(right < s.length()){
            char c = s.charAt(right++);
            //存在重复的字符，则移动左指针，直到滑动窗口中不含有该字符
            while(set.contains(c)){
                set.remove(s.charAt(left++));
            }
            set.add(c);
            res = Math.max(res, right-left);
        }
        return res;
    }
}
