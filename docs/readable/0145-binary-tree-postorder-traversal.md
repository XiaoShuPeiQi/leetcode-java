# 145 · 二叉树的后序遍历 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0145-binary-tree-postorder-traversal/

二叉树 · Binary Tree Postorder Traversal

## 题意

给定二叉树，按左子树、右子树、当前节点的顺序遍历，返回节点值列表。

官方原题：https://leetcode.cn/problems/binary-tree-postorder-traversal/

## 课堂示例

输入：root = [4, 2, 6, 1, 3, 5, 7]

预期结果：[1, 3, 2, 5, 7, 6, 4]

实际结果：[1, 3, 2, 5, 7, 6, 4]

测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）

## Java 解法

```java
import java.util.*;

class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        traversal(root, result);
        return result;
    }

    // ① 把当前子树的后序结果追加到 result，无需返回值。
    private void traversal(TreeNode node, List<Integer> result) {
        // ② 空节点没有内容，直接返回。
        if (node == null) {
            return;
        }
        // ③ 后序先完成两个孩子，最后记录自己。
        traversal(node.left, result);
        traversal(node.right, result);
        result.add(node.val);
    }
}
```

## 来源与版本

递归与其他解法均逐字核对原文类体；只作内部类命名、static 与外层适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0145BinaryTreePostorderTraversal.java

源码指纹：82ca45335d5449bc112f2b87186bda5a8a0b11a0013ec1b0f268dc2c58302ea0

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：86 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0145-binary-tree-postorder-traversal/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
