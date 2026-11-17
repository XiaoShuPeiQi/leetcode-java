# 110 · 平衡二叉树 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0110-balanced-binary-tree/

二叉树 · Balanced Binary Tree

## 题意

判断二叉树是否高度平衡，即所有节点的左右子树高度差都不超过 1。

官方原题：https://leetcode.cn/problems/balanced-binary-tree/

## 课堂示例

输入：root = [1, 2, 3, 4, null, null, null, 5]

预期结果：false

实际结果：false

测试来源：本地设计的代表性用例；原文未提供该题独立输入

## Java 解法

```java
import java.util.*;

class Solution {
    public boolean isBalanced(TreeNode root) {
        return height(root) != -1;
    }

    // ① 平衡时返回高度，不平衡时返回 -1；一次递归同时回答两件事。
    private int height(TreeNode node) {
        // ② 空树高度为 0，并且平衡。
        if (node == null) {
            return 0;
        }
        // ③ 先拿孩子结果；发现失衡就向上传 -1，否则合成当前高度。
        int leftHeight = height(node.left);
        // 孩子已失衡，父节点也必然失衡，无需再算另一边。
        if (leftHeight == -1) {
            return -1;
        }
        int rightHeight = height(node.right);
        if (rightHeight == -1 || Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }
}
```

## 来源与版本

递归与其他解法均逐字核对原文类体；只作内部类命名、static 与外层适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0110BalancedBinaryTree.java

源码指纹：4868210648d04b6a6b7c988b714a654657c2dc8ba0199be3290f9e9834399db4

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：49 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0110-balanced-binary-tree/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
