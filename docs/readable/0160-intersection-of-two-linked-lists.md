# 160 · 相交链表 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0160-intersection-of-two-linked-lists/

链表 · Intersection Of Two Linked Lists

## 题意

找出两个单链表共享的第一个节点对象。若两个链表无交点则返回 null，不能修改链表结构。

官方原题：https://leetcode.cn/problems/intersection-of-two-linked-lists/

## 课堂示例

输入：共享后缀C：8→4→5（这里的三个节点是真正共享的对象）

预期结果：8

实际结果：8

测试来源：飞书链表revision 115的实例走过程；707为力扣官方示例，其余为讲解用例。

## 解法

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

## Java 解法

```java
import java.util.*;

class Solution {
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
```

## 来源与版本

回放已核对的飞书链表revision 115主解；仅增加package、import、static与外层运行适配，算法逻辑不变。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/linkedlist/LC0160IntersectionOfTwoLinkedLists.java

源码指纹：8d99d2d7756d444bc31533784a489be7153111913e9da19fd270ab63692b0e31

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/HPizwBkbkiEf0zks7jUcvs8En2b（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：62 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0160-intersection-of-two-linked-lists/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
