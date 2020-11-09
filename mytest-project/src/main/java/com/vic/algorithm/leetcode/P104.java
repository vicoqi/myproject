package com.vic.algorithm.leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 104. 二叉树的最大深度
 *
 * 给定一个二叉树，找出其最大深度。
 * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
 *
 * 说明: 叶子节点是指没有子节点的节点。
 *
 * 示例：
 * 给定二叉树 [3,9,20,null,null,15,7]，
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * 返回它的最大深度 3 。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/maximum-depth-of-binary-tree
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author: wangqp
 * @create: 2020-08-17 15:00
 */
public class P104 {
    //思路，第一种方式：左右子树高度的最大值 +1 = 当前节点的高度
    public int maxDepth(TreeNode root) {
        if (root == null){
            return 0;
        }
        if (root.left == null && root.right == null){
            return 1;
        }
        return Math.max(maxDepth(root.left),maxDepth(root.right))+1;
    }

    //第二种方式：广度遍历，+1
    public int maxDepth_BFS(TreeNode root) {
        if (root == null){
            return 0;
        }
        int ceng = 1;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()){
            TreeNode cur = queue.poll();
            if (cur.left ==null && cur.right ==null){
                continue;
            }
            if (cur.left !=null){
                queue.add(cur.left);
            }
            if (cur.right !=null){
                queue.add(cur.right);
            }
            ceng++;
        }
        return ceng;
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
