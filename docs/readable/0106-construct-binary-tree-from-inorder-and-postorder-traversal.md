# 106 · 从中序与后序遍历序列构造二叉树 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0106-construct-binary-tree-from-inorder-and-postorder-traversal/

二叉树 · Construct Binary Tree From Inorder And Postorder Traversal

## 题意

给定同一棵二叉树的中序和后序遍历结果，重建二叉树并返回根节点。节点值互不相同。

官方原题：https://leetcode.cn/problems/construct-binary-tree-from-inorder-and-postorder-traversal/

## 课堂示例

输入：inorder = [1, 2, 3, 4, 5, 6, 7], postorder = [1, 3, 2, 5, 7, 6, 4]

预期结果：[4, 2, 6, 1, 3, 5, 7]

实际结果：[4, 2, 6, 1, 3, 5, 7]

测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）

## Java 解法

```java
import java.util.*;

class Solution {
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        Map<Integer, Integer> positions = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            positions.put(inorder[i], i);
        }
        return build(postorder, 0, postorder.length, 0, inorder.length, positions);
    }

    // ① 两组 [left, right) 区间描述同一棵子树；返回建好的根。
    private TreeNode build(int[] postorder, int postLeft, int postRight,
            int inLeft, int inRight, Map<Integer, Integer> positions) {
        // ② 空区间没有节点，返回 null 供父节点挂接。
        if (postLeft == postRight) {
            return null;
        }
        // ③ 后序末尾是根；中序定位根后，用左侧节点数切分后序。
        int rootValue = postorder[postRight - 1];
        int pivot = positions.get(rootValue);
        int leftSize = pivot - inLeft;
        TreeNode root = new TreeNode(rootValue);
        // 后序先左后右再根；左区间取前 leftSize 个，右区间排除末尾根。
        root.left = build(postorder, postLeft, postLeft + leftSize,
                inLeft, pivot, positions);
        root.right = build(postorder, postLeft + leftSize, postRight - 1,
                pivot + 1, inRight, positions);
        return root;
    }
}
```

## 来源与版本

递归与其他解法均逐字核对原文类体；只作内部类命名、static 与外层适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0106ConstructBinaryTreeFromInorderAndPostorderTraversal.java

源码指纹：1cf8a10d33e33acd6ebb7e1c9de927734f855974941e0b0b48f2b3710a5cbe31

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：121 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0106-construct-binary-tree-from-inorder-and-postorder-traversal/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
