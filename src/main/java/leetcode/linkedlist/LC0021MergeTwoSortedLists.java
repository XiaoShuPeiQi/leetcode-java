package leetcode.linkedlist;

import java.util.*;
import common.*;
import static common.ListSupport.*;

public class LC0021MergeTwoSortedLists {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "list1=[1,2,4], list2=[1,3,4]",
                "[1, 1, 2, 3, 4, 4]", String.valueOf(runSolution()));
    }

    // 测试来源：力扣示例与 Hot100 笔记。
    static Object runSolution() {
        ListNode a = fromValues(1, 2, 4);
        ListNode b = fromValues(1, 3, 4);
        return toValues(new Solution().mergeTwoLists(a, b));
    }

    static class Solution {
        public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
            ListNode dummy = new ListNode(0);
            ListNode tail = dummy;
            while (list1 != null && list2 != null) {
                if (list1.val <= list2.val) {
                    tail.next = list1;
                    list1 = list1.next;
                } else {
                    tail.next = list2;
                    list2 = list2.next;
                }
                tail = tail.next;
            }
            // 剩下的一段已经有序，可以直接接到结果末尾
            tail.next = list1 != null ? list1 : list2;
            return dummy.next;
        }
    }
}
