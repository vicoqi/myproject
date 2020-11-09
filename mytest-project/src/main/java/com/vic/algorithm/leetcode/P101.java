package com.vic.algorithm.leetcode;

/**
 *
 * 101. 对称二叉树
 * 给定一个二叉树，检查它是否是镜像对称的。
 *
 * 例如，二叉树 [1,2,2,3,4,4,3] 是对称的。
 *
 *     1
 *    / \
 *   2   2
 *  / \ / \
 * 3  4 4  3
 *  
 *
 * 但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的:
 *
 *     1
 *    / \
 *   2   2
 *    \   \
 *    3    3
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/symmetric-tree
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author: wangqp
 * @create: 2020-08-17 11:52
 */
public class P101 {


    public boolean isSymmetric(TreeNode root) {
        return isDuiCheng_xiangdeng(root,root);
    }

    //把一个树想象成两个树，看左右两边是否镜像对称
    /*
     *   2         2
     *  / \       / \
     * 3  4      4   3
     *
     */
    public boolean isDuiCheng_xiangdeng(TreeNode p,TreeNode q){
        if (p == null && q == null){
            return true;
        }
        if (p == null || q == null){
            return false;
        }
        if (p.val != q.val){
            return false;
        }
        return isDuiCheng_xiangdeng(p.left,q.right) && isDuiCheng_xiangdeng(p.right,q.left);
    }

    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

}
