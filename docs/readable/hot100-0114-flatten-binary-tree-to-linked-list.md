# 114 · 二叉树展开为链表 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0114-flatten-binary-tree-to-linked-list/

Hot100 · 二叉树 · Flatten Binary Tree to Linked List

## 题意

给你二叉树的根结点 `root` ，请你将它展开为一个单链表：

- 展开后的单链表应该同样使用 `TreeNode` ，其中 `right` 子指针指向链表中下一个结点，而左子指针始终为 `null` 。

- 展开后的单链表应该与二叉树 [**先序遍历**](<https://baike.baidu.com/item/%E5%85%88%E5%BA%8F%E9%81%8D%E5%8E%86/6442839?fr=aladdin>) 顺序相同。

**示例 1：**

![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0100-0199/0114.Flatten%20Binary%20Tree%20to%20Linked%20List/images/flaten.jpg)

```text
输入：root = [1,2,5,3,4,null,6]
输出：[1,null,2,null,3,null,4,null,5,null,6]
```

**示例 2：**

```text
输入：root = []
输出：[]
```

**示例 3：**

```text
输入：root = [0]
输出：[0]
```

**提示：**

- 树中结点数在范围 `[0, 2000]` 内

- `-100 <= Node.val <= 100`

**进阶：**你可以使用原地算法（`O(1)` 额外空间）展开这棵树吗？

官方原题：https://leetcode.cn/problems/flatten-binary-tree-to-linked-list

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0100-0199/0114.Flatten%20Binary%20Tree%20to%20Linked%20List/README.md

## 回放用例

输入：root=[1,2,5,3,4,null,6]

预期结果：[1, 2, 3, 4, 5, 6]

实际结果：[1, 2, 3, 4, 5, 6]

测试来源：力扣公开示例

## 状态含义

每次把当前节点的 right 接到 next，把 left 置空，再更新 next。

## 状态变化

倒着处理前序，让每个节点接到已经展开的后缀前面。

## Java 解法

```java
import java.util.*;

class Solution {
    private TreeNode next;

    public void flatten(TreeNode root) {
        next = null;
        traversal(root);
    }

    private void traversal(TreeNode node) {
        if (node == null) {
            return;
        }
        // 前序是根、左、右。倒着处理，才能先把后缀接好。
        traversal(node.right);
        traversal(node.left);
        node.right = next;
        node.left = null;
        next = node;
    }
}
```

## 来源与版本

学习扩展。按本题条件选择主解。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0114FlattenBinaryTreeToLinkedList.java

源码指纹：6b16440c5a6f6007411d8c48189d6189792fba67523648d7ea14491817b610d0

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：87 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0114-flatten-binary-tree-to-linked-list/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
