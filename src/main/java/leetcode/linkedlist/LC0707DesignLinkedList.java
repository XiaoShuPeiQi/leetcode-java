package leetcode.linkedlist;

import java.util.*;

public class LC0707DesignLinkedList {

    // 测试来源：飞书链表章节 revision 115 的实例走过程。
    // https://acn581cm6pdd.feishu.cn/wiki/HPizwBkbkiEf0zks7jUcvs8En2b
    public static void main(String[] args) {

        String expected="[2, 3]";
        String actual=runSolution();
        System.out.println("Input:");
        System.out.println("头插1 → 尾插3 → 下标1插2 → 读1 → 删1 → 读1");
        System.out.println("Expected:");System.out.println(expected);
        System.out.println("Actual:");System.out.println(actual);
        if(!expected.equals(actual))throw new AssertionError("FAIL");
        System.out.println("Result: PASS");
    }

    static String runSolution() {
        MyLinkedList obj=new MyLinkedList();
        obj.addAtHead(1);
        obj.addAtTail(3);
        obj.addAtIndex(1,2);
        int first=obj.get(1);
        obj.deleteAtIndex(1);
        int second=obj.get(1);
        return "["+first+", "+second+"]";
    }

    static class MyLinkedList {
        private static class ListNode {
            int val;
            ListNode next;
            ListNode(int val) {
                this.val = val;
            }
        }

        private final ListNode dummy;
        private int size;

        public MyLinkedList() {
            dummy = new ListNode(0);
            size = 0;
        }

        public int get(int index) {
            if (index < 0 || index >= size) {
                return -1;
            }
            ListNode cur = dummy.next;
            for (int i = 0; i < index; i++) {
                cur = cur.next;
            }
            return cur.val;
        }

        public void addAtHead(int val) {
            addAtIndex(0, val);
        }

        public void addAtTail(int val) {
            addAtIndex(size, val);
        }

        public void addAtIndex(int index, int val) {
            if (index < 0 || index > size) {
                return;
            }
            ListNode pre = dummy;
            for (int i = 0; i < index; i++) {
                pre = pre.next;
            }
            ListNode newNode = new ListNode(val);
            newNode.next = pre.next; // 先保住后缀
            pre.next = newNode;
            size++;
        }

        public void deleteAtIndex(int index) {
            if (index < 0 || index >= size) {
                return;
            }
            ListNode pre = dummy;
            for (int i = 0; i < index; i++) {
                pre = pre.next;
            }
            pre.next = pre.next.next;
            size--;
        }
    }
}
