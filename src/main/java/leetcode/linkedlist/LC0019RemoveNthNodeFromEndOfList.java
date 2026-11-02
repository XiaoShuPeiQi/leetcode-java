package leetcode.linkedlist;

import common.ListNode;
import java.util.*;

public class LC0019RemoveNthNodeFromEndOfList {

    static ListNode[] make(int[] values) {
        ListNode[] nodes=new ListNode[values.length];
        for(int i=0;i<values.length;i++)nodes[i]=new ListNode(values[i]);
        for(int i=0;i+1<nodes.length;i++)nodes[i].next=nodes[i+1];
        return nodes;
    }
    static String list(ListNode node) {
        java.util.ArrayList<Integer> values=new java.util.ArrayList<>();
        while(node!=null) { if(values.size()>30)throw new AssertionError("unexpected cycle");values.add(node.val);node=node.next; }
        return values.toString();
    }

    // 测试来源：飞书链表章节 revision 115 的实例走过程。
    // https://acn581cm6pdd.feishu.cn/wiki/HPizwBkbkiEf0zks7jUcvs8En2b
    public static void main(String[] args) {
        ListNode[] inputNodes=make(new int[]{1,2,3,4,5});
        String expected="[1, 2, 3, 5]";
        String actual=runSolution(inputNodes);
        System.out.println("Input:");
        System.out.println("head=[1,2,3,4,5], n=2");
        System.out.println("Expected:");System.out.println(expected);
        System.out.println("Actual:");System.out.println(actual);
        if(!expected.equals(actual))throw new AssertionError("FAIL");
        System.out.println("Result: PASS");
    }

    static String runSolution(ListNode[] inputNodes) {
        ListNode head=inputNodes[0];
        ListNode actual=new Solution().removeNthFromEnd(head, 2);
        return list(actual);
    }

    static class Solution {
        public ListNode removeNthFromEnd(ListNode head, int n) {
            ListNode dummy = new ListNode(0, head);
            ListNode fast = dummy;
            ListNode slow = dummy;
            for (int i = 0; i <= n; i++) {
                fast = fast.next; // 先拉开n+1条边
            }
            while (fast != null) {
                fast = fast.next;
                slow = slow.next;
            }
            slow.next = slow.next.next;
            return dummy.next;
        }
    }
}
