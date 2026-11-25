# 105 · 从前序与中序遍历序列构造二叉树（关联补充） — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0105-construct-binary-tree-from-preorder-and-inorder-traversal/

二叉树 · Construct Binary Tree From Preorder And Inorder Traversal

## 题意

给定两个整数数组 `preorder` 和 `inorder` ，其中 `preorder` 是二叉树的**先序遍历**， `inorder` 是同一棵树的**中序遍历**，请构造二叉树并返回其根节点。

**示例 1:**

![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0100-0199/0105.Construct%20Binary%20Tree%20from%20Preorder%20and%20Inorder%20Traversal/images/tree.jpg)

```text
输入: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
输出: [3,9,20,null,null,15,7]
```

**示例 2:**

```text
输入: preorder = [-1], inorder = [-1]
输出: [-1]
```

**提示:**

- `1 <= preorder.length <= 3000`

- `inorder.length == preorder.length`

- `-3000 <= preorder[i], inorder[i] <= 3000`

- `preorder` 和 `inorder` 均 **无重复** 元素

- `inorder` 均出现在 `preorder`

- `preorder` **保证** 为二叉树的前序遍历序列

- `inorder` **保证** 为二叉树的中序遍历序列

官方原题：https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-inorder-traversal

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0100-0199/0105.Construct%20Binary%20Tree%20from%20Preorder%20and%20Inorder%20Traversal/README.md

## 回放用例

输入：preorder = [4, 2, 1, 3, 6, 5, 7], inorder = [1, 2, 3, 4, 5, 6, 7]

预期结果：[4, 2, 6, 1, 3, 5, 7]

实际结果：[4, 2, 6, 1, 3, 5, 7]

测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）

## Java 解法

```java
import java.util.*;

class Solution {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        Map<Integer, Integer> positions = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            positions.put(inorder[i], i);
        }
        return build(preorder, 0, 0, inorder.length, positions);
    }

    // ① preLeft 指向子树根；[inLeft, inRight) 圈定这棵子树的全部节点。
    private TreeNode build(int[] preorder, int preLeft, int inLeft, int inRight,
            Map<Integer, Integer> positions) {
        // ② 中序区间为空，说明这棵子树为空。
        if (inLeft == inRight) {
            return null;
        }
        // ③ 前序起点是根；中序左侧数量决定两边的前序起点。
        TreeNode root = new TreeNode(preorder[preLeft]);
        int pivot = positions.get(root.val);
        int leftSize = pivot - inLeft;
        root.left = build(preorder, preLeft + 1, inLeft, pivot, positions);
        // 跳过前序里的根和整棵左子树，才到右子树起点。
        root.right = build(preorder, preLeft + leftSize + 1,
                pivot + 1, inRight, positions);
        return root;
    }
}
```

## 来源与版本

递归与其他解法均逐字核对原文类体；只作内部类命名、static 与外层适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0105ConstructBinaryTreeFromPreorderAndInorderTraversal.java

源码指纹：4fda960e973cd02d33832ac8612855d2635e7403113e3ff18b89990bc8558a66

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：114 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0105-construct-binary-tree-from-preorder-and-inorder-traversal/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
