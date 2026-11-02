package leetcode.linkedlist;

import common.ListNode;
import java.util.*;

public class LC0142LinkedListCycleII {

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
        ListNode[] inputNodes=make(new int[]{3,2,0,-4});inputNodes[inputNodes.length-1].next=inputNodes[1];
        String expected="2";
        String actual=runSolution(inputNodes);
        System.out.println("Input:");
        System.out.println("head=[3,2,0,-4], pos=1（尾部连回节点2）");
        System.out.println("Expected:");System.out.println(expected);
        System.out.println("Actual:");System.out.println(actual);
        if(!expected.equals(actual))throw new AssertionError("FAIL");
        System.out.println("Result: PASS");
    }

    static String runSolution(ListNode[] inputNodes) {
        ListNode head=inputNodes[0];
        ListNode actual=new Solution().detectCycle(head);
        if(actual!=inputNodes[1])throw new AssertionError("wrong entry object");
        return Integer.toString(actual.val);
    }

    static class Solution {
        public ListNode detectCycle(ListNode head) {
            ListNode slow = head;
            ListNode fast = head;
            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;
                if (slow == fast) {
                    ListNode index1 = head;
                    ListNode index2 = slow;
                    while (index1 != index2) {
                        index1 = index1.next;
                        index2 = index2.next;
                    }
                    return index1;
                }
            }
            return null;
        }
    }
}
