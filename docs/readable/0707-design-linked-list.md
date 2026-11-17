# 707 · 设计链表 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0707-design-linked-list/

链表 · Design Linked List

## 题意

实现链表的按下标访问、头插、尾插、按下标插入和删除。下标从 0 开始，访问无效下标返回 -1；插入位置等于长度时追加到末尾。

官方原题：https://leetcode.cn/problems/design-linked-list/

## 课堂示例

输入：头插1 → 尾插3 → 下标1插2 → 读1 → 删1 → 读1

预期结果：[2, 3]

实际结果：[2, 3]

测试来源：飞书链表revision 115的实例走过程；707为力扣官方示例，其余为讲解用例。

## 解法

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

## Java 解法

```java
import java.util.*;

class MyLinkedList {
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
```

## 来源与版本

回放已核对的飞书链表revision 115主解；仅增加package、import、static与外层运行适配，算法逻辑不变。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/linkedlist/LC0707DesignLinkedList.java

源码指纹：36fd31308616fc8b48f5697469a2101557f8ea81f88a1cd33c2594a757e3a917

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/HPizwBkbkiEf0zks7jUcvs8En2b（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：68 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0707-design-linked-list/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
