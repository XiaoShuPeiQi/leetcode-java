# 25 · K 个一组翻转链表 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0025-reverse-nodes-in-k-group/

Hot100 · 链表 · Reverse Nodes in k-Group

## 题意

将链表从头起每 k 个节点分为一组，翻转每个完整组的链接。最后不足 k 个节点时保持其顺序，返回新的头节点。

官方原题：https://leetcode.cn/problems/reverse-nodes-in-k-group/

## 课堂示例

输入：head=[1,2,3,4,5], k=2

预期结果：[2, 1, 4, 3, 5]

实际结果：[2, 1, 4, 3, 5]

测试来源：力扣公开示例

## 状态含义

first 在组内始终指向同一个节点，pre.next 指向每次头插后的新组头。先让 first 跳过被摘出的 node，再让 node 接到当前组头前面。整组完成后，pre 前移到 first。

## 状态变化

先确认够一组，再把组首后面的节点逐个提到组头。

## Java 解法

```java
import java.util.*;

class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0, head);
        ListNode pre = dummy;
        while (true) {
            ListNode end = pre;
            for (int i = 0; i < k; i++) {
                end = end.next;
                if (end == null) {
                    return dummy.next;
                }
            }

            ListNode first = pre.next;
            for (int i = 1; i < k; i++) {
                ListNode node = first.next;
                first.next = node.next;
                node.next = pre.next;//注意这里不能直接写first
                pre.next = node;
            }
            pre = first;//妙啊，最后first就在队尾了
        }
    }
}
```

## 来源与版本

学习扩展。按本题条件选择主解。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/linkedlist/LC0025ReverseNodesInKGroup.java

源码指纹：d2c6bd7592f72f1db904a781f71b91189555e4bc2d4afb87a025486281311d07

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：46 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0025-reverse-nodes-in-k-group/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
