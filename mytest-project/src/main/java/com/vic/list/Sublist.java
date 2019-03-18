package com.vic.list;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @Auther: wqp
 * @Date: 2019/3/13 18:00
 * @Description:
 *
 * 子 列表增加，父列表会增加
 * ****** but
 * 父列表增加，子类会报 currentModifException
 */
public class Sublist {
    public static void main(String[] args) {
        List<String> finalLockzone1 = new ArrayList<>();
        finalLockzone1.add("a1");
        List<String> finalLockzone = new ArrayList<>(finalLockzone1);
        finalLockzone.add("a");
        finalLockzone.add("b");
        finalLockzone.add("v");
        List<String> a = finalLockzone.subList(finalLockzone.size(),finalLockzone.size());
        System.out.println(a.toString());
        a.add("1");
        a.add("2");
        a.add("3");
        System.out.println(a.toString());
        System.out.println(finalLockzone.toString());
        System.out.println(finalLockzone1.toString());
    }
}
