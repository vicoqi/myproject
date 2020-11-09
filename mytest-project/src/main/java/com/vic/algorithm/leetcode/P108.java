package com.vic.algorithm.leetcode;

/**
 *
 * 108. 将有序数组转换为二叉搜索树
 *
 * 将一个按照升序排列的有序数组，转换为一棵高度平衡二叉搜索树。
 * 本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1。
 *
 * 示例:
 * 给定有序数组: [-10,-3,0,5,9],
 *
 * 一个可能的答案是：[0,-3,9,-10,null,5]，它可以表示下面这个高度平衡二叉搜索树：
 *
 *       0
 *      / \
 *    -3   9
 *    /   /
 *  -10  5
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/convert-sorted-array-to-binary-search-tree
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @date 2020/8/24
 */
public class P108 {
    public TreeNode sortedArrayToBST(int[] nums) {
        return composite(nums,0,nums.length-1);
    }

    public TreeNode composite(int[] nums,int start,int end) {
        if (nums.length==1){
            return new TreeNode(nums[0]);
        }
        if (start > end){
            return null;
        }
        //右边做根节点
        int fatherIndex = (start+end+1)/2;
        TreeNode fatherNode = new TreeNode(nums[fatherIndex]);
        fatherNode.left = composite(nums,start,fatherIndex-1);
        fatherNode.right = composite(nums,fatherIndex+1,end);
        return fatherNode;
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
