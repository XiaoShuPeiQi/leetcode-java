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
            ListNode pre = dummy;
            while (true) {
                ListNode end = pre;
                for (int i = 0; i < k; i++) {
                    end = end.next;
                    if (end == null) {
                        return dummy.next;
                    }
                }

                ListNode first = pre.next;
                for (int i = 1; i < k; i++) {
                    ListNode node = first.next;
                    first.next = node.next;
                    node.next = pre.next;//注意这里不能直接写first
                    pre.next = node;
                }
                pre = first;//妙啊，最后first就在队尾了
            }
        }
    }
}
