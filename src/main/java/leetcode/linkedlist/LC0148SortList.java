package leetcode.linkedlist;

import java.util.*;
import common.*;
import static common.ListSupport.*;

public class LC0148SortList {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "head=[4,2,1,3]",
                "[1, 2, 3, 4]", String.valueOf(runSolution()));
    }

    // 测试来源：力扣示例与 Hot100 笔记。
    static Object runSolution() {
        ListNode head = fromValues(4, 2, 1, 3);
        return toValues(new Solution().sortList(head));
    }

    static class Solution {
        public ListNode sortList(ListNode head) {
            //1.递归一部曲
            if (head == null || head.next == null) {
                return head;
            }
            ListNode slow = head;
            ListNode fast = head;
            while (fast.next != null && fast.next.next != null) {
                slow = slow.next;
                fast = fast.next.next;
            }
            ListNode right = slow.next;
            slow.next = null;
            //2.后序递归
            ListNode leftLink = sortList(head);
            ListNode rightLink = sortList(right);
            return merge(leftLink, rightLink);
        }

        private ListNode merge(ListNode a, ListNode b) {
            ListNode dummy = new ListNode(0);
            ListNode tail = dummy;
            while (a != null && b != null) {
                if (a.val <= b.val) {
                    tail.next = a;
                    a = a.next;
                } else {
                    tail.next = b;
                    b = b.next;
                }
                tail = tail.next;
            }
            tail.next = a != null ? a : b;
            return dummy.next;
        }
    }
}
