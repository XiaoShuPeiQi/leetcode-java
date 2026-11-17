# 142 · 环形链表 II — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0142-linked-list-cycle-ii/

Hot100 · 链表 · Linked List Cycle II

## 题意

返回单链表环的入口节点。如果从 head 出发没有环则返回 null，不能修改链表。

官方原题：https://leetcode.cn/problems/linked-list-cycle-ii/

## 课堂示例

输入：head=[3,2,0,-4], pos=1（尾部连回节点2）

预期结果：2

实际结果：2

测试来源：算法学习总笔记与项目已有 main

## 状态含义

第二阶段两指针每次各走一步，第一次同址就是入口。

## 状态变化

快慢指针相遇后，让链头和相遇点各出发一个指针，同速走到入口。

## Java 解法

```java
import java.util.*;

class Solution {
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                ListNode index1 = head;
                ListNode index2 = slow;
                while (index1 != index2) {
                    index1 = index1.next;
                    index2 = index2.next;
                }
                return index1;
            }
        }
        return null;
    }
}
```

## 来源与版本

沿用总笔记及仓库中本题的主解。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/linkedlist/LC0142LinkedListCycleII.java

源码指纹：6bbd980d84961283f6787356a34503fbe009f85bcf08033bf5c94e1466870c3c

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：23 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0142-linked-list-cycle-ii/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
