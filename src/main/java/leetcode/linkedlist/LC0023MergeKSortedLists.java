package leetcode.linkedlist;

import java.util.*;
import common.*;
import static common.ListSupport.*;

public class LC0023MergeKSortedLists {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "lists=[[1,4,5],[1,3,4],[2,6]]",
                "[1, 1, 2, 3, 4, 4, 5, 6]", String.valueOf(runSolution()));
    }

    // 测试来源：力扣示例与 Hot100 笔记。
    static Object runSolution() {
        ListNode[] lists = {fromValues(1, 4, 5), fromValues(1, 3, 4), fromValues(2, 6)};
        return toValues(new Solution().mergeKLists(lists));
    }

    static class Solution {
        public ListNode mergeKLists(ListNode[] lists) {
            PriorityQueue<ListNode> heap =
                    new PriorityQueue<>((a, b) -> Integer.compare(a.val, b.val));
            for (ListNode head : lists) {
                if (head != null) {
                    heap.offer(head);
                }
            }
            ListNode dummy = new ListNode(0);
            ListNode tail = dummy;
            while (!heap.isEmpty()) {
                ListNode node = heap.poll();
                tail.next = node;
                tail = node;
                if (node.next != null) {
                    heap.offer(node.next); // 只补上这一条链的下一个候选
                }
            }
            return dummy.next;
        }
    }
}
