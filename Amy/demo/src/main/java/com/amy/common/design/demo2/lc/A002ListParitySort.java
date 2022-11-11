package com.amy.common.design.demo2.lc;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuqingxin
 * <p>
 * 奇偶链表重新排序，奇数位置升序，偶数位置降序
 */
public class A002ListParitySort {
    @Data
    @NoArgsConstructor
    public static class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode();
        ListNode l2 = new ListNode();
        ListNode l3 = new ListNode();
        ListNode l4 = new ListNode();
        ListNode l5 = new ListNode();
        ListNode l6 = new ListNode();
        l1.val = 1;
        l2.val = 2;
        l3.val = 3;
        l4.val = 4;
        l5.val = 5;
        l6.val = 6;
        l1.next = l6;
        l6.next = l3;
        l3.next = l4;
        l4.next = l5;
        l5.next = l2;

        print(l1);

        print(Sort1(l1));
    }

    public static void print(ListNode node) {
        while (node != null) {
            System.out.print(node.val + " -> ");
            node = node.next;
        }
        System.out.println();
    }

    public static ListNode Sort1(ListNode head) {

        ListNode odd = new ListNode(-1);
        ListNode even = new ListNode(-1);
        ListNode oddHead = odd;
        ListNode evenHead = even;

        int count = 1;
        while (head != null) {
            if (count % 2 == 1) {
                even.next = head;
                even = even.next;
            } else {
                odd.next = head;
                odd = odd.next;
            }
            head = head.next;
            count++;
        }
        odd.next = null;
        even.next = null;

        print(oddHead.next);
        print(evenHead.next);

        ListNode resverse = resverse(oddHead.next);

        ListNode merge = merge(evenHead.next, resverse);

        return merge;
    }

    public static ListNode merge(ListNode l1, ListNode l2) {
        ListNode cur = new ListNode(-1);
        ListNode curHead = cur;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                cur.next = l1;
                l1 = l1.next;
            } else {
                cur.next = l2;
                l2 = l2.next;
            }
            cur = cur.next;
        }

        if (l1 != null) {
            cur.next = l1;
        }
        if (l2 != null) {
            cur.next = l2;
        }
        return curHead.next;

    }

    public static ListNode resverse(ListNode node) {
        ListNode cur = null;
        while (node != null) {
            ListNode next = node.next;
            node.next = cur;
            cur = node;
            node = next;
        }
        return cur;
    }


}
