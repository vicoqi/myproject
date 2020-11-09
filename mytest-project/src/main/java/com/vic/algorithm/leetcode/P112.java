package com.vic.algorithm.leetcode;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * 112. 路径总和
 * 给定一个二叉树和一个目标和，判断该树中是否存在根节点到叶子节点的路径，这条路径上所有节点值相加等于目标和。
 *
 * 说明: 叶子节点是指没有子节点的节点。
 *
 * 示例: 
 * 给定如下二叉树，以及目标和 sum = 22，
 *
 *               5
 *              / \
 *             4   8
 *            /   / \
 *           11  13  4
 *          /  \      \
 *         7    2      1
 * 返回 true, 因为存在目标和为 22 的根节点到叶子节点的路径 5->4->11->2。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/path-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author: wangqp
 * @create: 2020-09-03 16:09
 */
public class P112 {
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null){
            return false;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        Stack<Integer> stackValue = new Stack<>();
        stackValue.push(root.val);
        while (!stack.empty()){
            TreeNode treeNode = stack.pop();
            Integer value = stackValue.pop();
            if (treeNode.left == null && treeNode.right == null){
                if (value == sum){
                    return true;
                }
                continue;
            }

            if (treeNode.left != null){
                stack.push(treeNode.left);
                stackValue.push(value + treeNode.left.val);
            }

            if (treeNode.right != null){
                stack.push(treeNode.right);
                stackValue.push(value + treeNode.right.val);
            }
        }
        return false;
    }

    class TreeNode {
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
