package com.amy.common.design.demo2.lc;


import lombok.Data;

/**
 * @author xuqingxin
 * 链表类
 */

public class A001ListReverse {

    @Data
    public static class ListNode {
        int val;
        ListNode next;
    }


    /**
     * <p>
     * +-------------------------------------------+
     * |  递归版本链表反转
     * +-------------------------------------------+
     * </p>
     */
    public ListNode rerverseListNode(ListNode head) {
        if (head == null && head.next == null){
            return head;
        }
        ListNode p = rerverseListNode(head.next);
        head.next.next = head;
        head.next = null;
        return p;
    }

    /**
     * <p>
     * +-------------------------------------------+
     * | 普通链表反转1
     * +-------------------------------------------+
     * </p>
     */
    public static ListNode solution2(ListNode head) {
        ListNode cur = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = cur;
            cur = head;
            head = next;
        }
        return cur;
    }

    /**
     * <p>
     * +-------------------------------------------+
     * |    普通链表反转2
     * +-------------------------------------------+
     * </p>
     */
    public static ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode preNode = head;
        ListNode currentNode = head.next;
        //将原来头结点的next指针设置为null
        head.next = null;
        while (currentNode != null) {
            //保存指向下一个节点的指针
            ListNode saveNode = currentNode.next;
            //将当前节点的next指向前一个节点
            currentNode.next = preNode;
            preNode = currentNode;
            currentNode = saveNode;
        }
        return preNode;
    }


    /**
     * <p>
     * +-------------------------------------------+
     * | 主函数
     * +-------------------------------------------+
     * </p>
     */
    public static void main(String[] args) {
        ListNode l1 = new ListNode();
        ListNode l2 = new ListNode();
        ListNode l3 = new ListNode();
        ListNode l4 = new ListNode();
        l1.val = 1;
        l2.val = 2;
        l3.val = 3;
        l4.val = 4;
        l1.next = l2;
        l2.next = l3;
        l3.next = l4;

        print(l1);

        print(solution2(l1));

    }

    /**
     * <p>
     * +-------------------------------------------+
     * |    打印函数
     * +-------------------------------------------+
     * </p>
     */
    public static void print(ListNode node) {
        while (node != null) {
            System.out.print(node.val + " -> ");
            node = node.next;
        }
        System.out.println();
    }


}

