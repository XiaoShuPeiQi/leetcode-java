# 206 · 反转链表 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0206-reverse-linked-list/

Hot100 · 链表 · Reverse Linked List

## 题意

给你单链表的头节点 `head` ，请你反转链表，并返回反转后的链表。

**示例 1：**

![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0200-0299/0206.Reverse%20Linked%20List/images/rev1ex1.jpg)

```text
输入：head = [1,2,3,4,5]
输出：[5,4,3,2,1]
```

**示例 2：**

![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0200-0299/0206.Reverse%20Linked%20List/images/rev1ex2.jpg)

```text
输入：head = [1,2]
输出：[2,1]
```

**示例 3：**

```text
输入：head = []
输出：[]
```

**提示：**

- 链表中节点的数目范围是 `[0, 5000]`

- `-5000 <= Node.val <= 5000`

**进阶：**链表可以选用迭代或递归方式完成反转。你能否用两种方法解决这道题？

官方原题：https://leetcode.cn/problems/reverse-linked-list

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0200-0299/0206.Reverse%20Linked%20List/README.md

## 回放用例

输入：head=[1,2,3]

预期结果：[3, 2, 1]

实际结果：[3, 2, 1]

测试来源：算法学习总笔记与项目已有 main

## 状态含义

每一轮开始时，prev 是已翻转段的头，cur 是未处理段的头。

## 状态变化

逐个把当前节点的 next 转向已经翻好的前段。

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

沿用总笔记及仓库中本题的主解。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/linkedlist/LC0206ReverseLinkedList.java

源码指纹：9b97e3c89a775ba3159bebb191b7e375298353df8e45473256c384970f573f94

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：24 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0206-reverse-linked-list/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
