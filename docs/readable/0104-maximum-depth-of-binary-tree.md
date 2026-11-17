# 104 · 二叉树的最大深度 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0104-maximum-depth-of-binary-tree/

二叉树 · Maximum Depth Of Binary Tree

## 题意

求二叉树从根到最远叶子的路径包含多少个节点。空树的深度为 0。

官方原题：https://leetcode.cn/problems/maximum-depth-of-binary-tree/

## 课堂示例

输入：root = [4, 2, 6, 1, 3, 5, 7]

预期结果：3

实际结果：3

测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）

## Java 解法

```java
import java.util.*;

class Solution {
    // ① 返回当前子树的高度，不是当前节点距离根的深度。
    public int maxDepth(TreeNode root) {
        // ② 空树有 0 层；这样叶子才能由 max(0, 0) + 1 得到 1。
        if (root == null) {
            return 0;
        }
        // ③ 父高度依赖孩子：先拿左右高度，再取较大值加自己一层。
        int leftHeight = maxDepth(root.left);
        int rightHeight = maxDepth(root.right);
        return Math.max(leftHeight, rightHeight) + 1;
    }
}
```

## 来源与版本

递归与其他解法均逐字核对原文类体；只作内部类命名、static 与外层适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0104MaximumDepthOfBinaryTree.java

源码指纹：969fcaddd21585f532da11d22790b33c948db99ca1854122fa855abe9c765341

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：74 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0104-maximum-depth-of-binary-tree/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
