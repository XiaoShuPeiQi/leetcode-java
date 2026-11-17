# 530 · 二叉搜索树的最小绝对差 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0530-minimum-absolute-difference-in-bst/

二叉树 · Minimum Absolute Difference In BST

## 题意

从二叉搜索树选两个不同节点，求两者值的绝对差的最小值。

官方原题：https://leetcode.cn/problems/minimum-absolute-difference-in-bst/

## 课堂示例

输入：root = [4, 2, 6, 1, 3, 5, 7]

预期结果：1

实际结果：1

测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）

## Java 解法

```java
import java.util.*;

class Solution {
    private TreeNode previous;

    private int minimum;

    public int getMinimumDifference(TreeNode root) {
        previous = null;
        minimum = Integer.MAX_VALUE;
        traversal(root);
        return minimum;
    }

    // ① 中序访问当前子树；previous 和 minimum 保存相邻比较状态。
    private void traversal(TreeNode node) {
        // ② 空节点没有数值可比较。
        if (node == null) {
            return;
        }
        // ③ 先完成左边，当前才紧挨 previous；比较后更新前驱，再走右边。
        traversal(node.left);
        if (previous != null) {
            minimum = Math.min(minimum, node.val - previous.val);
        }
        // 比较完成再更新，不能先覆盖前驱再求差。
        previous = node;
        traversal(node.right);
    }
}
```

## 来源与版本

递归与其他解法均逐字核对原文类体；只作内部类命名、static 与外层适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0530MinimumAbsoluteDifferenceInBST.java

源码指纹：2ef09bbeaff2d3fa0db95434bb9a389b7801934f31df086da88a1e92585a874b

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：100 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0530-minimum-absolute-difference-in-bst/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
