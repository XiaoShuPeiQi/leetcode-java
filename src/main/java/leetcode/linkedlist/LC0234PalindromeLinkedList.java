package leetcode.linkedlist;

import java.util.*;
import common.*;
import static common.ListSupport.*;

public class LC0234PalindromeLinkedList {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "head=[1,2,2,1]",
                "true", String.valueOf(runSolution()));
    }

    // 测试来源：力扣示例与 Hot100 笔记。
    static Object runSolution() {
        ListNode head = fromValues(1, 2, 2, 1);
        boolean res = new Solution().isPalindrome(head);
        if (!toValues(head).equals(List.of(1, 2, 2, 1))) throw new AssertionError("链表没有恢复");
        return res;
    }

    static class Solution {
        public boolean isPalindrome(ListNode head) {
            ListNode slow = head;
            ListNode fast = head;
            // slow 停在前半段末尾，奇数长度时停在中点
            while (fast.next != null && fast.next.next != null) {
                slow = slow.next;
                fast = fast.next.next;
            }
            ListNode second = reverse(slow.next);
            ListNode left = head;
            ListNode right = second;
            boolean res = true;
            while (right != null) {
                if (left.val != right.val) {
                    res = false;
                    break;
                }
                left = left.next;
                right = right.next;
            }
            slow.next = reverse(second); // 比较完恢复输入链表
            return res;
        }

        private ListNode reverse(ListNode cur) {
            ListNode prev = null;
            while (cur != null) {
                ListNode next = cur.next;
                cur.next = prev;
                prev = cur;
                cur = next;
            }
            return prev;
        }
    }
}
