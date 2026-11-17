# 2 · 两数相加 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0002-add-two-numbers/

Hot100 · 链表 · Add Two Numbers

## 题意

两个非空链表分别按从低位到高位表示两个非负整数。每个节点存一位数字。把两个数相加，并用同样的链表格式返回结果。

官方原题：https://leetcode.cn/problems/add-two-numbers/

## 课堂示例

输入：l1=[2,4,3], l2=[5,6,4]

预期结果：[7, 0, 8]

实际结果：[7, 0, 8]

测试来源：力扣公开示例

## 状态含义

只要还有数字或进位，就继续产生下一位。

## 状态变化

从个位开始逐位相加，把进位留给下一轮。

## Java 解法

```java
import java.util.*;

class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        int carry = 0;
        while (l1 != null || l2 != null || carry != 0) {
            int sum = carry;
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }
            tail.next = new ListNode(sum % 10); // 当前位只保留个位
            tail = tail.next;
            carry = sum / 10; // 十位交给下一轮
        }
        return dummy.next;
    }
}
```

## 来源与版本

学习扩展。按本题条件选择主解。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/linkedlist/LC0002AddTwoNumbers.java

源码指纹：9531fdbdfb190dc337c7c28bc3c3313a647100d0bd6ae6cc971a2c6bebb5de3d

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：43 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0002-add-two-numbers/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
