# 111 · 二叉树的最小深度 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0111-minimum-depth-of-binary-tree/

二叉树 · Minimum Depth Of Binary Tree

## 题意

求从二叉树根节点到最近叶子所经过的节点数；叶子没有孩子。空树返回 0。

官方原题：https://leetcode.cn/problems/minimum-depth-of-binary-tree/

## 课堂示例

输入：root = [1, null, 2]

预期结果：2

实际结果：2

测试来源：飞书原文 4.4 的最小反例

## Java 解法

```java
import java.util.*;

class Solution {
    // ① 返回从当前根到最近叶子的节点数。
    public int minDepth(TreeNode root) {
        // ② 空树为 0，但空孩子不能当作可走的叶子路径。
        if (root == null) {
            return 0;
        }
        // ③ 单孩子只能走存在的一边；两边都在才取 min，最后加自己。
        if (root.left == null) {
            return minDepth(root.right) + 1;
        }
        if (root.right == null) {
            return minDepth(root.left) + 1;
        }
        int leftDepth = minDepth(root.left);
        int rightDepth = minDepth(root.right);
        return Math.min(leftDepth, rightDepth) + 1;
    }
}
```

## 来源与版本

递归与其他解法均逐字核对原文类体；只作内部类命名、static 与外层适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0111MinimumDepthOfBinaryTree.java

源码指纹：77d27e3e00fba1f746022c703126b63219a691af0934c4c63abf8f18db5a5d10

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：14 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0111-minimum-depth-of-binary-tree/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
