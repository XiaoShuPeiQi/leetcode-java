package leetcode.linkedlist;

import java.util.*;
import common.*;
import static common.ListSupport.*;

public class LC0025ReverseNodesInKGroup {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "head=[1,2,3,4,5], k=2",
                "[2, 1, 4, 3, 5]", String.valueOf(runSolution()));
    }

    // 测试来源：力扣示例与 Hot100 笔记。
    static Object runSolution() {
        ListNode head = fromValues(1, 2, 3, 4, 5);
        return toValues(new Solution().reverseKGroup(head, 2));
    }

    static class Solution {
        public ListNode reverseKGroup(ListNode head, int k) {
            ListNode dummy = new ListNode(0, head);
            ListNode before = dummy;
            while (true) {
                ListNode end = before;
                for (int i = 0; i < k; i++) {
                    end = end.next;
                    if (end == null) {
                        return dummy.next; // 不足一整组，保留剩余节点
                    }
                }

                ListNode after = end.next;
                ListNode start = before.next;
                ListNode cur = start;
                ListNode prev = after; // 原组首翻转后成为组尾，先连好后缀
                while (cur != after) {
                    ListNode next = cur.next;
                    cur.next = prev;
                    prev = cur;
                    cur = next;
                }
                before.next = end;
                before = start;
            }
        }
    }
}
