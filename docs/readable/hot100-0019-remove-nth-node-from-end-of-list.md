# 19 · 删除链表的倒数第 N 个结点 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0019-remove-nth-node-from-end-of-list/

Hot100 · 链表 · Remove Nth Node From End of List

## 题意

删除单链表中从尾部数第 n 个节点，返回删除后的头节点。

官方原题：https://leetcode.cn/problems/remove-nth-node-from-end-of-list/

## 课堂示例

输入：head=[1,2,3,4,5], n=2

预期结果：[1, 2, 3, 5]

实际结果：[1, 2, 3, 5]

测试来源：算法学习总笔记与项目已有 main

## 状态含义

同步前进时两者间距始终为 n+1 条边。

## 状态变化

用固定间距让慢指针恰好停在待删节点前面。

## Java 解法

```java
import java.util.*;

class Solution {
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
```

## 来源与版本

沿用总笔记及仓库中本题的主解。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/linkedlist/LC0019RemoveNthNodeFromEndOfList.java

源码指纹：8a0e066d2a05f93fa4e0227ca0cbab2c8432778b165d169b031fc601e43a5f9c

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：24 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0019-remove-nth-node-from-end-of-list/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
