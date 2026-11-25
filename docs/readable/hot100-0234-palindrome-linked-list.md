# 234 · 回文链表 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0234-palindrome-linked-list/

Hot100 · 链表 · Palindrome Linked List

## 题意

给你一个单链表的头节点 `head` ，请你判断该链表是否为回文链表。如果是，返回 `true` ；否则，返回 `false` 。

**示例 1：**

![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0200-0299/0234.Palindrome%20Linked%20List/images/pal1linked-list.jpg)

```text
输入：head = [1,2,2,1]
输出：true
```

**示例 2：**

![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0200-0299/0234.Palindrome%20Linked%20List/images/pal2linked-list.jpg)

```text
输入：head = [1,2]
输出：false
```

**提示：**

- 链表中节点数目在范围`[1, 10^5]` 内

- `0 <= Node.val <= 9`

**进阶：**你能否用 `O(n)` 时间复杂度和 `O(1)` 空间复杂度解决此题？

官方原题：https://leetcode.cn/problems/palindrome-linked-list

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0200-0299/0234.Palindrome%20Linked%20List/README.md

## 回放用例

输入：head=[1,2,2,1]

预期结果：true

实际结果：true

测试来源：力扣公开示例

## 状态含义

只比较后半段的长度。奇数长度时，中间节点留在前半段。

## 状态变化

翻转后半段，把首尾对照变成两条链同时向前比较。

## Java 解法

```java
import java.util.*;

class Solution {
    public boolean isPalindrome(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        // slow 停在前半段末尾，奇数长度时停在中点
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode second = reverse(slow.next);
        ListNode left = head;
        ListNode right = second;
        boolean res = true;
        while (right != null) {
            if (left.val != right.val) {
                res = false;
                break;
            }
            left = left.next;
            right = right.next;
        }
        slow.next = reverse(second); // 比较完恢复输入链表
        return res;
    }

    private ListNode reverse(ListNode cur) {
        ListNode prev = null;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return prev;
    }
}
```

## 来源与版本

学习扩展。按本题条件选择主解。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/linkedlist/LC0234PalindromeLinkedList.java

源码指纹：62e1b20dcbe7139295ac7ac9a023e068baa43ea5d648137a4efcdd61ecdfd73c

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：57 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0234-palindrome-linked-list/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
