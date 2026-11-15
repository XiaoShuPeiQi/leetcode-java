package leetcode.linkedlist;

import java.util.*;
import common.*;
import static common.ListSupport.*;

public class LC0138CopyListWithRandomPointer {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "head=[[1,1],[2,1]]",
                "[[1, 1], [2, 1]]", String.valueOf(runSolution()));
    }

    // 测试来源：力扣示例与 Hot100 笔记。
    static Object runSolution() {
        Node first = new Node(1);
        Node second = new Node(2);
        first.next = second;
        first.random = second;
        second.random = second;
        Node copy = new Solution().copyRandomList(first);
        if (copy == first || copy == second || copy.next == first || copy.next == second
                || copy.next == copy || copy.next.next != null || copy.random != copy.next
                || copy.next.random != copy.next || first.next != second
                || first.random != second || second.random != second) {
            throw new AssertionError("副本身份或引用不正确");
        }
        return List.of(List.of(copy.val, 1), List.of(copy.next.val, 1));
    }

    static class Node {
        int val;
        Node next;
        Node random;

        Node(int val) {
            this.val = val;
        }
    }

    static class Solution {
        public Node copyRandomList(Node head) {
            HashMap<Node, Node> map = new HashMap<>();
            for (Node cur = head; cur != null; cur = cur.next) {
                Node node = new Node(cur.val);
                map.put(cur, node);
            }
            for (Node cur = head; cur != null; cur = cur.next) {
                Node node = map.get(cur);
                node.next = map.get(cur.next);
                node.random = map.get(cur.random);
            }
            return map.get(head);
        }
    }
}
