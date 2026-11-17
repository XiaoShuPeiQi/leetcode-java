# 235 · 二叉搜索树的最近公共祖先 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0235-lowest-common-ancestor-of-a-binary-search-tree/

二叉树 · Lowest Common Ancestor Of ABinary Search Tree

## 题意

在二叉搜索树中找 p 和 q 的最近公共祖先。一个节点也可以是自己的祖先。

官方原题：https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-search-tree/

## 课堂示例

输入：root = [4, 2, 6, 1, 3, 5, 7], p = 节点 1, q = 节点 3

预期结果：[2, 1, 3]

实际结果：[2, 1, 3]

测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）

## Java 解法

```java
import java.util.*;

class Solution {
    // ① 返回当前子树中 p、q 的最近公共祖先，题目保证目标存在。
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // ② 空树没有可返回的祖先。
        if (root == null) {
            return null;
        }
        // ③ 两个目标同侧就继续走；分居两侧或命中当前时，当前就是祖先。
        if (p.val < root.val && q.val < root.val) {
            return lowestCommonAncestor(root.left, p, q);
        }
        if (p.val > root.val && q.val > root.val) {
            return lowestCommonAncestor(root.right, p, q);
        }
        return root;
    }
}
```

## 来源与版本

递归与其他解法均逐字核对原文类体；只作内部类命名、static 与外层适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0235LowestCommonAncestorOfABinarySearchTree.java

源码指纹：7c66cc2053c9fd85479a40c53f4710704a3041258a81628cc95bd8ea19787f1e

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：11 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0235-lowest-common-ancestor-of-a-binary-search-tree/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
