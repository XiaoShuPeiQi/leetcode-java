# 617 · 合并二叉树 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0617-merge-two-binary-trees/

二叉树 · Merge Two Binary Trees

## 题意

叠放两棵二叉树。重叠位置的节点值相加，只有一棵树有节点的位置保留其值，返回合并后的根节点。

官方原题：https://leetcode.cn/problems/merge-two-binary-trees/

## 课堂示例

输入：root1 = [1, 3, 2, 5], root2 = [2, 1, 3, null, 4, null, 7]

预期结果：[3, 4, 5, 5, 4, null, 7]

实际结果：[3, 4, 5, 5, 4, null, 7]

测试来源：本地设计的代表性用例；原文未提供该题独立输入

## Java 解法

```java
import java.util.*;

class Solution {
    // ① 两个参数表示同一位置的子树；返回合并后的根。
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        // ② 一边为空，直接复用另一边；两边都空自然返回 null。
        if (root1 == null) {
            return root2;
        }
        // 第二棵空，第一棵原样保留；这里不会新建独立副本。
        if (root2 == null) {
            return root1;
        }
        // ③ 两边都有才相加；孩子可能换根，必须接回两次递归的返回值。
        root1.val += root2.val;
        root1.left = mergeTrees(root1.left, root2.left);
        root1.right = mergeTrees(root1.right, root2.right);
        return root1;
    }
}
```

## 来源与版本

递归与其他解法均逐字核对原文类体；只作内部类命名、static 与外层适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0617MergeTwoBinaryTrees.java

源码指纹：fb723974f1b1d0a62e846192fb04b161da499799b43ccb539b6839811406d1f1

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：41 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0617-merge-two-binary-trees/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
