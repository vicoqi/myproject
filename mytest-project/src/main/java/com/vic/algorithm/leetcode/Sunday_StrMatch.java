package com.vic.algorithm.leetcode;

/**
 * https://mp.weixin.qq.com/s/kdxLfy1AWs50duJMmiT__w
 *
 * 实现 strStr() 函数。给定一个 haystack 字符串和一个 needle 字符串，
 * 在 haystack 字符串中找出 needle 字符串出现的第一个位置 (从0开始)。如果不存在，则返回  -1。
 *
 * 输入: haystack = "hello", needle = "ll"
 * 输出: 2
 *
 * 输入: haystack = "aaaaa", needle = "bba"
 * 输出: -1
 *
 * 过程：1.字符串不匹配，下一个字符是否在 aim内
 *      2.如果不在，则继续字符串匹配
 *      3.如果字符 倒着 匹配在 aim 中的话，就开始往后移几位 匹配。
 */
public class Sunday_StrMatch {

//    public int strStr(String origin, String aim) {
//        //origin index
//        int i = 0;
//        while (i < origin.length()){
//            if (isPiPei(i,origin,aim)){
//                return i;
//            }else {
//                //倒着找 这个字符，并返回下标
//                Integer backIndex = backInAim(i+aim.length(),origin,aim);
//                if (backIndex == null){
//                    i = i + aim.length() + 1;
//                }else {
//                    i = i + backIndex +1;
//                }
//            }
//        }
//    }
//
//    private boolean isPiPei(int i, String origin, String aim) {
//
//    }
}
