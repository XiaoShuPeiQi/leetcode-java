package leetcode.linkedlist;

import java.util.*;
import common.*;
import static common.ListSupport.*;

public class LC0002AddTwoNumbers {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "l1=[2,4,3], l2=[5,6,4]",
                "[7, 0, 8]", String.valueOf(runSolution()));
    }

    // 测试来源：力扣示例与 Hot100 笔记。
    static Object runSolution() {
        ListNode a = fromValues(2, 4, 3);
        ListNode b = fromValues(5, 6, 4);
        return toValues(new Solution().addTwoNumbers(a, b));
    }

    static class Solution {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            ListNode dummy = new ListNode(0);
            ListNode tail = dummy;
            int carry = 0;
            while (l1 != null || l2 != null || carry != 0) {
                int sum = carry;
                if (l1 != null) {
                    sum += l1.val;
                    l1 = l1.next;
                }
                if (l2 != null) {
                    sum += l2.val;
                    l2 = l2.next;
                }
                tail.next = new ListNode(sum % 10); // 当前位只保留个位
                tail = tail.next;
                carry = sum / 10; // 十位交给下一轮
            }
            return dummy.next;
        }
    }
}
