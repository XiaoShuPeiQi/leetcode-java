# 404 · 左叶子之和 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0404-sum-of-left-leaves/

二叉树 · Sum Of Left Leaves

## 题意

给定二叉树的根节点 `root` ，返回所有左叶子之和。

**示例 1：**

![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0400-0499/0404.Sum%20of%20Left%20Leaves/images/leftsum-tree.jpg)

```text
输入: root = [3,9,20,null,null,15,7]
输出: 24
解释: 在这个二叉树中，有两个左叶子，分别是 9 和 15，所以返回 24
```

**示例 2:**

```text
输入: root = [1]
输出: 0
```

**提示:**

- 节点数在 `[1, 1000]` 范围内

- `-1000 <= Node.val <= 1000`

官方原题：https://leetcode.cn/problems/sum-of-left-leaves

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0400-0499/0404.Sum%20of%20Left%20Leaves/README.md

## 回放用例

输入：root = [4, 2, 6, 1, 3, 5, 7]

预期结果：6

实际结果：6

测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）

## Java 解法

```java
import java.util.*;

class Solution {
    // ① 返回当前子树内部所有左叶子的和，不把当前根自动算进去。
    public int sumOfLeftLeaves(TreeNode root) {
        // ② 空树贡献为 0。
        if (root == null) {
            return 0;
        }
        // ③ 从父节点认出左叶子；不是叶子就继续找，再加右树内部贡献。
        int leftSum;
        if (isLeaf(root.left)) {
            leftSum = root.left.val;
        } else {
            leftSum = sumOfLeftLeaves(root.left);
        }
        // 右孩子本身不计入，但右子树内部仍可能有左叶子。
        return leftSum + sumOfLeftLeaves(root.right);
    }

    private boolean isLeaf(TreeNode node) {
        return node != null && node.left == null && node.right == null;
    }
}
```

## 来源与版本

递归与其他解法均逐字核对原文类体；只作内部类命名、static 与外层适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0404SumOfLeftLeaves.java

源码指纹：fb80cc5b0d23e22a39ff16369d1075478e353359d8d6d596706d92eb2c74e067

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：61 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0404-sum-of-left-leaves/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
