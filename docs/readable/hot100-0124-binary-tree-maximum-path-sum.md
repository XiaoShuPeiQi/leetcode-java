# 124 · 二叉树中的最大路径和 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0124-binary-tree-maximum-path-sum/

Hot100 · 二叉树 · Binary Tree Maximum Path Sum

## 题意

在非空二叉树中取一条不重复节点的非空路径，可以从任意节点开始或结束。返回最大的路径节点值之和。

官方原题：https://leetcode.cn/problems/binary-tree-maximum-path-sum/

## 课堂示例

输入：root=[-10,9,20,null,null,15,7]

预期结果：42

实际结果：42

测试来源：力扣公开示例

## 状态含义

gain 返回包含当前节点的单支最大和，best 记录任意两端之间的最大和。

## 状态变化

每个节点都试一次左右合并，向父节点只交一条收益最大的支路。

## Java 解法

```java
import java.util.*;

class Solution {
    private int best;

    public int maxPathSum(TreeNode root) {
        best = root.val;
        gain(root);
        return best;
    }

    private int gain(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int left = Math.max(0, gain(node.left));
        int right = Math.max(0, gain(node.right));
        best = Math.max(best, node.val + left + right);
        // 交给父节点的路径只能接其中一边。
        return node.val + Math.max(left, right);
    }
}
```

## 来源与版本

学习扩展。按本题条件选择主解。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0124BinaryTreeMaximumPathSum.java

源码指纹：8c559347795d437fdc3dec27ce3893abc6acb483608958938e6aff58eb9957e9

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：64 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0124-binary-tree-maximum-path-sum/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
