package com.vic.algorithm.leetcode;

/**
 * 83. 删除排序链表中的重复元素
 * 给定一个排序链表，删除所有重复的元素，使得每个元素只出现一次。
 *
 * 输入: 1->1->2
 * 输出: 1->2
 *
 * 输入: 1->1->2->3->3
 * 输出: 1->2->3
 *
 * @author: wangqp
 * @create: 2020-08-12 15:28
 */
public class DeleteDuplicates {
    public ListNode deleteDuplicates(ListNode head) {
        ListNode cur = head;
        while (cur.next !=null){
            if (cur.val == cur.next.val){
                cur.next = cur.next.next;
            } else {
                cur = cur.next;
            }
        }
        return head;
    }

    public class ListNode {
          int val;
          ListNode next;
          ListNode(int x) { val = x; }
   }
}
