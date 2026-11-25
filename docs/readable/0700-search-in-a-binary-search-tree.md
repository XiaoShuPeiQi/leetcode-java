# 700 · 二叉搜索树中的搜索 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0700-search-in-a-binary-search-tree/

二叉树 · Search In ABinary Search Tree

## 题意

给定二叉搜索树（BST）的根节点 `root` 和一个整数值 `val`。

你需要在 BST 中找到节点值等于 `val` 的节点。 返回以该节点为根的子树。 如果节点不存在，则返回 `null` 。

**示例 1:**

![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0700-0799/0700.Search%20in%20a%20Binary%20Search%20Tree/images/tree1.jpg)

```text
输入：root = [4,2,7,1,3], val = 2
输出：[2,1,3]
```

**示例 2:**

![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0700-0799/0700.Search%20in%20a%20Binary%20Search%20Tree/images/tree2.jpg)

```text
输入：root = [4,2,7,1,3], val = 5
输出：[]
```

**提示：**

- 树中节点数在 `[1, 5000]` 范围内

- `1 <= Node.val <= 10^7`

- `root` 是二叉搜索树

- `1 <= val <= 10^7`

官方原题：https://leetcode.cn/problems/search-in-a-binary-search-tree

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0700-0799/0700.Search%20in%20a%20Binary%20Search%20Tree/README.md

## 回放用例

输入：root = [4, 2, 6, 1, 3, 5, 7], val = 2

预期结果：[2, 1, 3]

实际结果：[2, 1, 3]

测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）

## Java 解法

```java
import java.util.*;

class Solution {
    // ① 返回目标节点本身；没找到返回 null。
    public TreeNode searchBST(TreeNode root, int val) {
        // ② 空树表示没找到，值相等则已经命中，都无需继续。
        if (root == null || root.val == val) {
            return root;
        }
        // ③ BST 排除另一侧，只递归可能含目标的一边，并把结果返回。
        if (val < root.val) {
            return searchBST(root.left, val);
        }
        return searchBST(root.right, val);
    }
}
```

## 来源与版本

递归与其他解法均逐字核对原文类体；只作内部类命名、static 与外层适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0700SearchInABinarySearchTree.java

源码指纹：b3f451f8d3e06b85e99b6cf02a64282650c8199a2da0d52d3b66405d289ddc97

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：9 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0700-search-in-a-binary-search-tree/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
