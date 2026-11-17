# 206 · 反转链表 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0206-reverse-linked-list/

链表 · Reverse Linked List

## 题意

反转单链表的连接方向，返回反转后的头节点。

官方原题：https://leetcode.cn/problems/reverse-linked-list/

## 课堂示例

输入：head=[1,2,3]

预期结果：[3, 2, 1]

实际结果：[3, 2, 1]

测试来源：飞书链表revision 115的实例走过程；707为力扣官方示例，其余为讲解用例。

## 解法

    static class Solution {
        public ListNode reverseList(ListNode head) {
            ListNode prev = null;
            ListNode cur = head;
            while (cur != null) {
                ListNode next = cur.next; // 改线前保存原后继
                cur.next = prev;
                prev = cur;
                cur = next;
            }
            return prev;
        }
    }

## Java 解法

```java
import java.util.*;

class Solution {
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode next = cur.next; // 改线前保存原后继
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return prev;
    }
}
```

## 来源与版本

回放已核对的飞书链表revision 115主解；仅增加package、import、static与外层运行适配，算法逻辑不变。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/linkedlist/LC0206ReverseLinkedList.java

源码指纹：9b97e3c89a775ba3159bebb191b7e375298353df8e45473256c384970f573f94

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/HPizwBkbkiEf0zks7jUcvs8En2b（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：24 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0206-reverse-linked-list/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
