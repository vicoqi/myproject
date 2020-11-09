package com.vic.algorithm.leetcode;

/**剑指 Offer 48. 最长不含重复字符的子字符串
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

    public int length(String str){
        char[] chars = str.toCharArray();
        StringBuilder sub = new StringBuilder();
        for (int i=0;i<chars.length;i++){
            sub.append(chars[i]);
            for (int j=i+1;j<chars.length;j++){
                if (sub.indexOf(String.valueOf(chars[j]))>0){
                    sub.append(chars[j]);
                } else {
                    sub.setLength(0);
                }
            }
        }
        return sub.length();
    }

//    public int mylength(String str){
//        char[] chars = str.toCharArray();
//        int max = 0;
//        StringBuilder sub = new StringBuilder();
//        sub.append(chars[0]);
//        for (int i=1;i<chars.length;i++){
//            //在子串中的下标
//            int index = sub.indexOf(String.valueOf(chars[i]));
//            if (>0){
//                max = Math.max(max,sub.length());
//
//            }
//
//
//        }
//        return sub.length();
//    }
}
