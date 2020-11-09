package com.vic.algorithm.leetcode;

/**
 *
 * 21. 合并两个有序链表
 * @author: wangqp
 * @create: 2020-08-04 17:14
 */
public class MergeTwoLists {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode cur1 = l1;
        ListNode cur2 = l2;
        ListNode head = new ListNode();
        ListNode last = head;
        while (cur1 !=null || cur2 !=null) {

            ListNode curNew = null;

            if (cur1 == null){
                curNew = new ListNode(cur2.val);
                cur2 = cur2.next;
            } else if (cur2 == null){
                curNew = new ListNode(cur1.val);
                cur1 = cur1.next;
            } else if(cur1.val <= cur2.val){
                curNew = new ListNode(cur1.val);
                cur1 = cur1.next;
            } else {
                curNew = new ListNode(cur2.val);
                cur2 = cur2.next;
            }

            last.next = curNew;
            last = curNew;
        }
        return head.next;
    }


    //递归
    public ListNode mergeTwoListsMore(ListNode l1, ListNode l2) {
        if(l1 == null) {
            return l2;
        }
        if(l2 == null) {
            return l1;
        }

        if(l1.val < l2.val) {
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        }
    }

//    leetcode-cn.com/problems/merge-two-sorted-lists/solution/hua-jie-suan-fa-21-he-bing-liang-ge-you-xu-lian-bi/



    class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
}
