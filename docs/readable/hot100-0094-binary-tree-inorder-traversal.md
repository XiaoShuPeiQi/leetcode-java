# 94 · 二叉树的中序遍历 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0094-binary-tree-inorder-traversal/

Hot100 · 二叉树 · Binary Tree Inorder Traversal

## 题意

给定一个二叉树的根节点 `root` ，返回 *它的 **中序** 遍历* 。

**示例 1：**

![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0000-0099/0094.Binary%20Tree%20Inorder%20Traversal/images/inorder_1.jpg)

```text
输入：root = [1,null,2,3]
输出：[1,3,2]
```

**示例 2：**

```text
输入：root = []
输出：[]
```

**示例 3：**

```text
输入：root = [1]
输出：[1]
```

**提示：**

- 树中节点数目在范围 `[0, 100]` 内

- `-100 <= Node.val <= 100`

**进阶:** 递归算法很简单，你可以通过迭代算法完成吗？

官方原题：https://leetcode.cn/problems/binary-tree-inorder-traversal

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0000-0099/0094.Binary%20Tree%20Inorder%20Traversal/README.md

## 回放用例

输入：root = [4, 2, 6, 1, 3, 5, 7]

预期结果：[1, 2, 3, 4, 5, 6, 7]

实际结果：[1, 2, 3, 4, 5, 6, 7]

测试来源：算法学习总笔记与项目已有 main

## 状态含义

递归函数负责把这一棵子树的中序结果追加到同一个列表。

## 状态变化

每棵子树都按左、根、右的顺序访问。

## Java 解法

```java
import java.util.*;

class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        traversal(root, result);
        return result;
    }

    // ① 把当前子树的中序结果追加到 result，无需返回值。
    private void traversal(TreeNode node, List<Integer> result) {
        // ② 空节点没有内容，直接返回。
        if (node == null) {
            return;
        }
        // ③ 中序要等左边完成，才记录自己，再处理右边。
        traversal(node.left, result);
        result.add(node.val);
        traversal(node.right, result);
    }
}
```

## 来源与版本

沿用总笔记及仓库中本题的主解。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0094BinaryTreeInorderTraversal.java

源码指纹：9b78e45967d122ed29780ec6dc467db658d48663fc7e7b1e09e644638aa7dcac

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：86 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0094-binary-tree-inorder-traversal/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
