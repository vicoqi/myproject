package com.vic.algorithm.leetcode;

/**
 * 给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
 *
 * 输入: 123
 * 输出: 321
 *
 * 整型溢出问题 -2^31 ~ 2^31-1
 *
 * @author: wangqp
 * @create: 2020-08-10 17:45
 */
public class ReverseInteger {
    public int reverse(int x) {
        int newInt = 0;
        int zhengchu = x;
        int yu=0;
        do {
            yu = zhengchu%10;
            newInt = newInt*10 + yu;
            zhengchu = zhengchu/10;
        }while (zhengchu!=0);
        return newInt;
    }
}
