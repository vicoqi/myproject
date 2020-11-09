package com.vic.algorithm.leetcode;

/**
 * 编写一个函数来查找字符串数组中的最长公共前缀。
 *
 * 如果不存在公共前缀，返回空字符串 ""。
 *
 * 输入: ["flower","flow","flight"]
 * 输出: "fl"
 *
 * 输入: ["dog","racecar","car"]
 * 输出: ""
 * @author: wangqp
 * @create: 2020-08-10 18:53
 */
public class LongestCommonPrefix {
    public String longestCommonPrefix(String[] strs) {
        StringBuilder sb = new StringBuilder("");
        for (int i=0;;i++){
            boolean flag = true;
            char f = strs[0].charAt(i);
            if ("".equals(f)) {
                return sb.toString();
            }
            for (int j=1;j<strs.length;j++){
                //字符串长度比较
                if (i >= strs[j].length() || strs[j].charAt(i) != f){
                    flag = false;
                    break;
                }
            }
            if (flag){
                sb.append(f);
            }
            return sb.toString();
        }
    }
}
