package com.vic.algorithm.leetcode;

import java.util.Map;

/**
 * 111. 二叉树的最小深度
 *
 * 给定一个二叉树，找出其最小深度。
 * 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
 * 说明: 叶子节点是指没有子节点的节点。
 *
 *示例:
 *
 * 给定二叉树 [3,9,20,null,null,15,7],
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * 返回它的最小深度  2.
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/minimum-depth-of-binary-tree
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author: wangqp
 * @create: 2020-09-01 18:22
 */

public class P111 {

    public int minDepth(P100.TreeNode root) {
        if (root == null){
            return 0;
        }
        return Math.min(minDepth(root.left),minDepth(root.right))+1;
    }

}



//理解题意：左右子树的 最小高度，感觉要从上往下进行 递归
