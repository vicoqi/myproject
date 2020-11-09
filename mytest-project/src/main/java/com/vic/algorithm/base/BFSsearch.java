package com.vic.algorithm.base;

import com.vic.algorithm.leetcode.P104;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * @author: wangqp
 * @create: 2020-07-08 17:33
 */
public class BFSsearch {
    @Test
    public void bfs(){
        Queue<TreeNode> treeNodeQueue = new LinkedList<>();
        Set<TreeNode> close = new HashSet<>();
        while (!treeNodeQueue.isEmpty()){

        }

    }


    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
