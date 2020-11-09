package com.vic.algorithm.leetcode;

import java.util.*;

/**
 * 给定一个二叉树，返回其节点值自底向上的层次遍历。 （即按从叶子节点所在层到根节点所在的层，逐层从左向右遍历）
 *
 * 例如：
 * 给定二叉树 [3,9,20,null,null,15,7],
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * 返回其自底向上的层次遍历为：
 *
 * [
 *   [15,7],
 *   [9,20],
 *   [3]
 * ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/binary-tree-level-order-traversal-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author: wangqp
 * @create: 2020-08-24 17:03
 */
public class P107 {
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> ret = new LinkedList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        Set<TreeNode> close = new HashSet<>();

        while (!queue.isEmpty()){
            TreeNode cur = queue.poll();
            if (cur == null) {
                break;
            }
            if (close.contains(cur)){
                continue;
            }
            List<Integer> vals = new LinkedList<>();
            if(cur.left != null){
                queue.offer(cur.left);
                vals.add(cur.left.val);
            }
            if(cur.right != null){
                queue.offer(cur.right);
                vals.add(cur.right.val);
            }
            if (vals.size()>0){
                ret.add(0,vals);
            }
            close.add(cur);
        }
        return ret;
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
