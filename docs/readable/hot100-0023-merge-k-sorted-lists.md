# 23 · 合并 K 个升序链表 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0023-merge-k-sorted-lists/

Hot100 · 链表 · Merge k Sorted Lists

## 题意

给定 k 个升序链表，把它们合并成一个升序链表，返回合并后的头节点。

官方原题：https://leetcode.cn/problems/merge-k-sorted-lists/

## 课堂示例

输入：lists=[[1,4,5],[1,3,4],[2,6]]

预期结果：[1, 1, 2, 3, 4, 4, 5, 6]

实际结果：[1, 1, 2, 3, 4, 4, 5, 6]

测试来源：力扣公开示例

## 状态含义

堆中每条尚未耗尽的链最多贡献一个候选。

## 状态变化

小顶堆只保存每条链当前最有资格接入的节点。

## Java 解法

```java
import java.util.*;

class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> heap =
                new PriorityQueue<>((a, b) -> Integer.compare(a.val, b.val));
        for (ListNode head : lists) {
            if (head != null) {
                heap.offer(head);
            }
        }
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        while (!heap.isEmpty()) {
            ListNode node = heap.poll();
            tail.next = node;
            tail = node;
            if (node.next != null) {
                heap.offer(node.next); // 只补上这一条链的下一个候选
            }
        }
        return dummy.next;
    }
}
```

## 来源与版本

学习扩展。按本题条件选择主解。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/linkedlist/LC0023MergeKSortedLists.java

源码指纹：156b3b1b77fc7067da2f7b3c61571969aed9e622f31f433badede71d815b7720

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：109 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0023-merge-k-sorted-lists/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
