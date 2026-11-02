package leetcode.linkedlist;

import common.ListNode;
import java.util.*;

public class LC0160IntersectionOfTwoLinkedLists {

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
        ListNode[] inputA=make(new int[]{4,1});ListNode[] inputB=make(new int[]{5,6,1});ListNode[] shared=make(new int[]{8,4,5});inputA[inputA.length-1].next=shared[0];inputB[inputB.length-1].next=shared[0];
        String expected="8";
        String actual=runSolution(inputA,inputB,shared);
        System.out.println("Input:");
        System.out.println("共享后缀C：8→4→5（这里的三个节点是真正共享的对象）");
        System.out.println("Expected:");System.out.println(expected);
        System.out.println("Actual:");System.out.println(actual);
        if(!expected.equals(actual))throw new AssertionError("FAIL");
        System.out.println("Result: PASS");
    }

    static String runSolution(ListNode[] inputA, ListNode[] inputB, ListNode[] shared) {
        ListNode headA=inputA[0],headB=inputB[0];
        ListNode actual=new Solution().getIntersectionNode(headA,headB);
        if(actual!=shared[0])throw new AssertionError("wrong shared object");
        return Integer.toString(actual.val);
    }

    static class Solution {
        public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
            int lenA = getLength(headA);
            int lenB = getLength(headB);
            ListNode curA = headA;
            ListNode curB = headB;
            while (lenA > lenB) {
                curA = curA.next;
                lenA--;
            }
            while (lenB > lenA) {
                curB = curB.next;
                lenB--;
            }
            while (curA != curB) {
                curA = curA.next;
                curB = curB.next;
            }
            return curA;
        }

        private int getLength(ListNode head) {
            int length = 0;
            while (head != null) {
                length++;
                head = head.next;
            }
            return length;
        }
    }
}
