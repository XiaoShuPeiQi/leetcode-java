# 226 · 翻转二叉树 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0226-invert-binary-tree/

Hot100 · 二叉树 · Invert Binary Tree

## 题意

给你一棵二叉树的根节点 `root` ，翻转这棵二叉树，并返回其根节点。

**示例 1：**

![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0200-0299/0226.Invert%20Binary%20Tree/images/invert1-tree.jpg)

```text
输入：root = [4,2,7,1,3,6,9]
输出：[4,7,2,9,6,3,1]
```

**示例 2：**

![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0200-0299/0226.Invert%20Binary%20Tree/images/invert2-tree.jpg)

```text
输入：root = [2,1,3]
输出：[2,3,1]
```

**示例 3：**

```text
输入：root = []
输出：[]
```

**提示：**

- 树中节点数目范围在 `[0, 100]` 内

- `-100 <= Node.val <= 100`

官方原题：https://leetcode.cn/problems/invert-binary-tree

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0200-0299/0226.Invert%20Binary%20Tree/README.md

## 回放用例

输入：root = [4, 2, 6, 1, 3, 5, 7]

预期结果：[4, 6, 2, 7, 5, 3, 1]

实际结果：[4, 6, 2, 7, 5, 3, 1]

测试来源：算法学习总笔记与项目已有 main

## 状态含义

当前根仍是原对象，改变的是它的左右连接。

## 状态变化

每个节点交换左右孩子，再把两个孩子的子树也翻转。

## Java 解法

```java
import java.util.*;

class Solution {
    // ① 返回当前子树翻转后的根；操作直接修改原节点。
    public TreeNode invertTree(TreeNode root) {
        // ② 空树翻转后仍为空。
        if (root == null) {
            return null;
        }
        // ③ 交换两侧，再递归翻转交换后的孩子；当前根仍是 root。
        TreeNode temporary = root.left;
        root.left = root.right;
        root.right = temporary;
        invertTree(root.left);
        invertTree(root.right);
        return root;
    }
}
```

## 来源与版本

沿用总笔记及仓库中本题的主解。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0226InvertBinaryTree.java

源码指纹：dbb6689af85f623eaaa5128332d0ea917d32c5cf1524c8509819e21b41f6bba3

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：95 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0226-invert-binary-tree/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
