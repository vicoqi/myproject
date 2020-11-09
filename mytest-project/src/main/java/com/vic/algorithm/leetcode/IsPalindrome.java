package com.vic.algorithm.leetcode;

/**
 * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 *
 * 输入: 121
 * 输出: true
 *
 * 输入: -121
 * 输出: false
 * 解释: 从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
 *
 * @author: wangqp
 * @create: 2020-08-10 18:31
 */
public class IsPalindrome {
    public boolean isPalindrome(int x) {
        if (x<0 || (x%10==0 && x!=0)){
            return false;
        }
        int yu = 0;
        int zhengchu = x;
        int newInt = 0;
        do {
            yu = zhengchu%10;
            newInt = newInt*10 + yu;
            if (zhengchu==newInt){
                return true;
            }
            zhengchu = zhengchu/10;
            if (zhengchu==newInt){
                return true;
            }
        }while (zhengchu > newInt);
        return false;
    }
}

/** 思路总结 遇到 整数， 先取余取整 看下
 *
 * @date 2020/9/1
 */
