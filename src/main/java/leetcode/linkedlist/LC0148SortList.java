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
            if (head == null || head.next == null) {
                return head;
            }
            ListNode slow = head;
            ListNode fast = head.next;
            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;
            }
            ListNode right = slow.next;
            slow.next = null; // 分成两条独立的链，递归才会缩小规模
            ListNode sortedLeft = sortList(head);
            ListNode sortedRight = sortList(right);
            return merge(sortedLeft, sortedRight);
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
