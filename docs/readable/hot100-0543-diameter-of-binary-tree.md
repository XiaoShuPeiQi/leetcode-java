# 543 · 二叉树的直径 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0543-diameter-of-binary-tree/

Hot100 · 二叉树 · Diameter of Binary Tree

## 题意

在二叉树中任取两个节点，求它们之间路径能包含的最大边数。路径可以经过根，也可以完全位于子树。

官方原题：https://leetcode.cn/problems/diameter-of-binary-tree/

## 课堂示例

输入：root=[1,2,3,4,5]

预期结果：3

实际结果：3

测试来源：力扣公开示例

## 状态含义

直径按边数计，空子树高度为 0，叶子向上回传 1。

## 状态变化

每个节点都试一次，让左、右向下最长的两段在这里接起来。

## Java 解法

```java
import java.util.*;

class Solution {
    private int diameter;

    public int diameterOfBinaryTree(TreeNode root) {
        diameter = 0;
        height(root);
        return diameter;
    }

    // 返回当前子树的高度，同时检查经过当前节点的路径。
    private int height(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int left = height(node.left);
        int right = height(node.right);
        diameter = Math.max(diameter, left + right);
        return Math.max(left, right) + 1;
    }
}
```

## 来源与版本

学习扩展。按本题条件选择主解。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0543DiameterOfBinaryTree.java

源码指纹：b344baf01f1c1e53a820be3836c3cf410f3ae33d1f49c8220b2c891b2d40f963

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：64 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0543-diameter-of-binary-tree/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
