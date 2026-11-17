# 102 · 二叉树的层序遍历 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0102-binary-tree-level-order-traversal/

二叉树 · Binary Tree Level Order Traversal

## 题意

给定二叉树根节点 root，按深度从浅到深读取节点。同一层由左至右收集成一个列表，返回所有层组成的二维列表。空树返回空列表。

官方原题：https://leetcode.cn/problems/binary-tree-level-order-traversal/

## 课堂示例

输入：root = [4, 2, 6, 1, 3, 5, 7]

预期结果：[[4], [2, 6], [1, 3, 5, 7]]

实际结果：[[4], [2, 6], [1, 3, 5, 7]]

测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）

## Java 解法

```java
import java.util.*;

class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        traversal(root, 0, result);
        return result;
    }

    // ① depth 是层下标，根为 0；节点写入 result[depth]。
    private void traversal(TreeNode node, int depth, List<List<Integer>> result) {
        // ② 空节点不属于任何层，直接返回。
        if (node == null) {
            return;
        }
        // ③ 首次到达这一层先建列表，再记当前值并继续下一层。
        if (depth == result.size()) {
            result.add(new ArrayList<>());
        }
        result.get(depth).add(node.val);
        // 孩子属于下一层；先左后右保证同层从左到右。
        traversal(node.left, depth + 1, result);
        traversal(node.right, depth + 1, result);
    }
}
```

## 来源与版本

递归与其他解法均逐字核对原文类体；只作内部类命名、static 与外层适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0102BinaryTreeLevelOrderTraversal.java

源码指纹：6a572db99efa2f111f28e900829c730d87afa8a491cd237edcc2f454e6ae1a6c

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：96 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0102-binary-tree-level-order-traversal/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
