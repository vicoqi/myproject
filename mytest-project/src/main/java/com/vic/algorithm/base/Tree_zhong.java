package com.vic.algorithm.base;

import java.util.ArrayList;
import java.util.List;
import static com.vic.algorithm.leetcode.P101.TreeNode;

/**
 * 树 中序遍历
 * @author: wangqp
 * @create: 2020-09-21 12:03
 */
public class Tree_zhong {

    public List<TreeNode> foreach(TreeNode head){
        List<TreeNode> ret = new ArrayList<>();

        zhongDigui(head,ret);

        return ret;
    }

    // 左->根->右
    private void zhongDigui(TreeNode head, List<TreeNode> ret) {
        if (head == null){
            return;
        }
        zhongDigui(head.left,ret);
        ret.add(head);
        zhongDigui(head.right,ret);
    }

    //根-》左-》右  前序遍历
    private void qianxuDigui(TreeNode head, List<TreeNode> ret){
        if (head == null){
            return;
        }
        ret.add(head);
        qianxuDigui(head.left,ret);
        qianxuDigui(head.right,ret);
    }
    //左->右->根） 后续遍历
    private void houxuDigui(TreeNode head, List<TreeNode> ret){
        if (head == null){
            return;
        }
        qianxuDigui(head.left,ret);
        qianxuDigui(head.right,ret);
        ret.add(head);
    }


}
