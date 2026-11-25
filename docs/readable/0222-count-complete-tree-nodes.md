# 222 · 完全二叉树的节点个数 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0222-count-complete-tree-nodes/

二叉树 · Count Complete Tree Nodes

## 题意

给你一棵 **完全二叉树** 的根节点 `root` ，求出该树的节点个数。

[完全二叉树](<https://baike.baidu.com/item/%E5%AE%8C%E5%85%A8%E4%BA%8C%E5%8F%89%E6%A0%91/7773232?fr=aladdin>) 的定义如下：在完全二叉树中，除了最底层节点可能没填满外，其余每层节点数都达到最大值，并且最下面一层的节点都集中在该层最左边的若干位置。若最底层为第 `h` 层（从第 0 层开始），则该层包含 `1~ 2^h` 个节点。

**示例 1：**

![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0200-0299/0222.Count%20Complete%20Tree%20Nodes/images/complete.jpg)

```text
输入：root = [1,2,3,4,5,6]
输出：6
```

**示例 2：**

```text
输入：root = []
输出：0
```

**示例 3：**

```text
输入：root = [1]
输出：1
```

**提示：**

- 树中节点的数目范围是`[0, 5 * 10^4]`

- `0 <= Node.val <= 5 * 10^4`

- 题目数据保证输入的树是 **完全二叉树**

**进阶：**遍历树来统计节点是一种时间复杂度为 `O(n)` 的简单解决方案。你可以设计一个更快的算法吗？

官方原题：https://leetcode.cn/problems/count-complete-tree-nodes

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0200-0299/0222.Count%20Complete%20Tree%20Nodes/README.md

## 回放用例

输入：root = [1, 2, 3, 4, 5, 6]

预期结果：6

实际结果：6

测试来源：本地设计的代表性用例；原文未提供该题独立输入

## Java 解法

```java
import java.util.*;

class Solution {
    // ① 返回当前完全子树的节点总数。
    public int countNodes(TreeNode root) {
        // ② 空树没有节点，返回 0。
        if (root == null) {
            return 0;
        }
        // ③ 先看是否整棵满；能用公式就直接算，否则递归左右再加自己。
        int leftHeight = leftHeight(root);
        int rightHeight = 0;
        TreeNode cur = root;
        while (cur != null) {
            rightHeight++;
            cur = cur.right;
        }
        if (leftHeight == rightHeight) {
            // 完全树的两端同高，才能判定整棵满；h 层共 2^h - 1 个节点。
            return (int) ((1L << leftHeight) - 1);
        }
        return countNodes(root.left) + countNodes(root.right) + 1;
    }

    private int leftHeight(TreeNode node) {
        int height = 0;
        while (node != null) {
            height++;
            node = node.left;
        }
        return height;
    }
}
```

## 来源与版本

递归与其他解法均逐字核对原文类体；只作内部类命名、static 与外层适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0222CountCompleteTreeNodes.java

源码指纹：695921bad1e42cab57c2c24901e8d5c44ced12047942020d3630c0f522114f6f

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：102 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0222-count-complete-tree-nodes/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
