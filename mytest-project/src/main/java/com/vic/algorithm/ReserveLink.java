package com.vic.algorithm;

import lombok.Getter;
import lombok.Setter;
import org.omg.CORBA.NO_IMPLEMENT;

/**
 * @description: 如何实现一个高效的单向链表逆序输出？
 * @author: wangqp
 * @create: 2020-06-18 16:45
 **/
public class ReserveLink {
    public static void main(String[] args) {
        //创建一个链表
        ReserveLink link = new ReserveLink();

        ReserveLink.Node first = link.new Node();
        first.setValue(1);

        link.constructLink(first,link.new Node());

        System.out.println(first);

        System.out.println(link.prettyLink(first));

        //逆序链表
        Node last = null;
        Node cur = first;
        while (cur.next !=null){
            Node temp = cur.next;
            cur.next = last;
            last = cur;
            cur = temp;
        }
        cur.next = last;
        System.out.println(link.prettyLink(cur));
    }

    @Getter@Setter
    class Node{
        private Integer value;
        private Node next;
        @Override
        public String toString() {
            return value.toString();
        }
    }

    private void constructLink(Node last,Node cur){
        if(last.getValue() == 10){
            return;
        }
        cur.setValue(last.getValue()+1);
        last.setNext(cur);
        constructLink(cur,new Node());
    }

    private String prettyLink(Node first){
        StringBuilder sb = new StringBuilder();
        do {
            sb.append(first.toString()).append("|");
            first = first.getNext();
        }while (first!=null);
        return sb.toString();
    }

}
