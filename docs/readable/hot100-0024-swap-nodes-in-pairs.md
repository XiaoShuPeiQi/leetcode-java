# 24 · 两两交换链表中的节点 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0024-swap-nodes-in-pairs/

Hot100 · 链表 · Swap Nodes in Pairs

## 题意

给你一个链表，两两交换其中相邻的节点，并返回交换后链表的头节点。你必须在不修改节点内部的值的情况下完成本题（即，只能进行节点交换）。

**示例 1：**

![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0000-0099/0024.Swap%20Nodes%20in%20Pairs/images/swap_ex1.jpg)

```text
输入：head = [1,2,3,4]
输出：[2,1,4,3]
```

**示例 2：**

```text
输入：head = []
输出：[]
```

**示例 3：**

```text
输入：head = [1]
输出：[1]
```

**提示：**

- 链表中节点的数目在范围 `[0, 100]` 内

- `0 <= Node.val <= 100`

官方原题：https://leetcode.cn/problems/swap-nodes-in-pairs

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0000-0099/0024.Swap%20Nodes%20in%20Pairs/README.md

## 回放用例

输入：head=[1,2,3,4,5]，pre=dummy

预期结果：[2, 1, 4, 3, 5]

实际结果：[2, 1, 4, 3, 5]

测试来源：算法学习总笔记与项目已有 main

## 状态含义

交换前，pre.next 是 first，pre.next.next 是 second。改完三条连接后，从 pre 再走两步会到原来的 first，也就是新组尾。把 pre 移到那里再处理下一组。

## 状态变化

把相邻两个节点当一组，交换它们并接回前后。

## Java 解法

```java
import java.util.*;

class Solution {
    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0, head);
        ListNode pre = dummy;
        while (pre.next != null && pre.next.next != null) {
            ListNode first = pre.next;
            ListNode second = pre.next.next;

            pre.next = second;
            first.next = second.next; // 在second改线前保住后缀
            second.next = first;

            pre = pre.next.next;
        }
        return dummy.next;
    }
}
```

## 来源与版本

沿用总笔记及仓库中本题的主解。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/linkedlist/LC0024SwapNodesInPairs.java

源码指纹：585d333e0e1ee5cc4f6dc96b883ccb3357e5dc98030b4d972f4af890044bbcaf

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：22 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0024-swap-nodes-in-pairs/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
