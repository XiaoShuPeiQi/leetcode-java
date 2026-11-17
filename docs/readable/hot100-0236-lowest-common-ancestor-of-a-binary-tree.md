# 236 · 二叉树的最近公共祖先 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0236-lowest-common-ancestor-of-a-binary-tree/

Hot100 · 二叉树 · Lowest Common Ancestor of a Binary Tree

## 题意

在普通二叉树中找 p 和 q 的最近公共祖先。一个节点也可以是自己的祖先。

官方原题：https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-tree/

## 课堂示例

输入：root = [4, 2, 6, 1, 3, 5, 7], p = 节点 1, q = 节点 3

预期结果：[2, 1, 3]

实际结果：[2, 1, 3]

测试来源：算法学习总笔记与项目已有 main

## 状态含义

递归返回找到的目标或已经确定的最近公共祖先。目标节点本身也可能是祖先。

## 状态变化

左右子树各找到一个目标时，当前节点就是它们的汇合点。

## Java 解法

```java
import java.util.*;

class Solution {
    // ① 返回本子树发现的目标，或已经确定的公共祖先；未发现返回 null。
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // ② 空返回 null；遇到 p/q 返回自己，因为目标也可以是祖先。
        if (root == null || root == p || root == q) {
            return root;
        }
        // ③ 先让左右报告；两边都有则在当前汇合，一边有则继续向上传。
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        // 左右各有目标（或已确定结果），它们在当前节点汇合。
        if (left != null && right != null) {
            return root;
        }
        if (left != null) {
            return left;
        }
        return right;
    }
}
```

## 来源与版本

沿用总笔记及仓库中本题的主解。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0236LowestCommonAncestorOfABinaryTree.java

源码指纹：5cdde85f78af51ed6355d679958aa845fd4ba04387a988e34bf3f43cbffe5eee

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：63 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0236-lowest-common-ancestor-of-a-binary-tree/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
