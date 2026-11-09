package common;

import java.util.ArrayList;
import java.util.List;

// 仅用于本地示例的构建与校验。
public final class ListSupport {
    private ListSupport() {
    }

    public static ListNode fromValues(int... values) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        for (int value : values) {
            tail.next = new ListNode(value);
            tail = tail.next;
        }
        return dummy.next;
    }

    public static List<Integer> toValues(ListNode head) {
        List<Integer> values = new ArrayList<>();
        while (head != null) {
            if (values.size() >= 1000) {
                throw new AssertionError("示例链表出现循环或长度异常");
            }
            values.add(head.val);
            head = head.next;
        }
        return values;
    }
}
