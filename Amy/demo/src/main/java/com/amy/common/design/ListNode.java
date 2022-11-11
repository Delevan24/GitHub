package com.amy.common.design;

import lombok.Data;

/**
 * @author xuqingxin
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
@Data
public abstract class ListNode {
    int val;
    ListNode next;
}
