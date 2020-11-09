package com.vic.algorithm.leetcode;

/**
 * 110. 平衡二叉树
 * 给定一个二叉树，判断它是否是高度平衡的二叉树。
 *
 * 本题中，一棵高度平衡二叉树定义为：
 *
 * 一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过1。
 *
 * 示例 1:
 *
 * 给定二叉树 [3,9,20,null,null,15,7]
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * 返回 true 。
 *
 * 示例 2:
 *
 * 给定二叉树 [1,2,2,3,3,null,null,4,4]
 *
 *        1
 *       / \
 *      2   2
 *     / \
 *    3   3
 *   / \
 *  4   4
 * 返回 false 。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/balanced-binary-tree
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author: wangqp
 * @create: 2020-09-01 16:31
 */
public class P110 {

    //感觉调用 height 方法计算有点儿多啊。双层递归
    //需要判断自己的子树的高度差，还得递归判断 子树是否平衡
    public boolean isBalanced(P100.TreeNode root) {
        return height(root.left) - height(root.right) <= 1 && isBalanced(root.left) && isBalanced(root.right);
    }
    //计算节点的高度，即左右子树的最大高度 +1
    public int height(P100.TreeNode node){
        if (node == null){
            return 0;
        }
        return Math.max(height(node.left),height(node.right))+1;
    }
}

//总结平衡二叉树，左右子树高度差不超过 1，且左右子树自己为平衡树。
