# 138 · 随机链表的复制 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0138-copy-list-with-random-pointer/

Hot100 · 链表 · Copy List with Random Pointer

## 题意

复制一个带 next 与 random 指针的链表。新节点的值和指针关系要对应原链表，所有非空指针应指向新节点。

官方原题：https://leetcode.cn/problems/copy-list-with-random-pointer/

## 课堂示例

输入：head=[[1,1],[2,1]]

预期结果：[[1, 1], [2, 1]]

实际结果：[[1, 1], [2, 1]]

测试来源：力扣公开示例

## 状态含义

第一遍 new 出 node，并把原节点 cur 对应到它。第二遍用 map.get(cur) 找回同一个副本，再分别查 cur.next 和 cur.random。原引用为空时，查表结果也是空。

## 状态变化

先造出所有副本节点，再把两种指针翻译到副本。

## Java 解法

```java
import java.util.*;

class Solution {
    public Node copyRandomList(Node head) {
        HashMap<Node, Node> map = new HashMap<>();
        for (Node cur = head; cur != null; cur = cur.next) {
            Node node = new Node(cur.val);
            map.put(cur, node);
        }
        for (Node cur = head; cur != null; cur = cur.next) {
            Node node = map.get(cur);
            node.next = map.get(cur.next);
            node.random = map.get(cur.random);
        }
        return map.get(head);
    }
}
```

## 来源与版本

学习扩展。按本题条件选择主解。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/linkedlist/LC0138CopyListWithRandomPointer.java

源码指纹：4490dacf5eed4e78826cac4c97c9c2f461f09491d1d33cd7509076e7f654f1b1

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：20 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0138-copy-list-with-random-pointer/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
