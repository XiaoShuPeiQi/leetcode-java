# 701 · 二叉搜索树中的插入操作 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0701-insert-into-a-binary-search-tree/

二叉树 · Insert Into ABinary Search Tree

## 题意

给定二叉搜索树（BST）的根节点 `root` 和要插入树中的值 `value` ，将值插入二叉搜索树。 返回插入后二叉搜索树的根节点。 输入数据 **保证** ，新值和原始二叉搜索树中的任意节点值都不同。

**注意**，可能存在多种有效的插入方式，只要树在插入后仍保持为二叉搜索树即可。 你可以返回 **任意有效的结果** 。

**示例 1：**

![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0700-0799/0701.Insert%20into%20a%20Binary%20Search%20Tree/images/insertbst.jpg)

```text
输入：root = [4,2,7,1,3], val = 5
输出：[4,2,7,1,3,5]
解释：另一个满足题目要求可以通过的树是：


![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0700-0799/0701.Insert%20into%20a%20Binary%20Search%20Tree/images/bst.jpg)
```

**示例 2：**

```text
输入：root = [40,20,60,10,30,50,70], val = 25
输出：[40,20,60,10,30,50,70,null,null,25]
```

**示例 3：**

```text
输入：root = [4,2,7,1,3,null,null,null,null,null,null], val = 5
输出：[4,2,7,1,3,5]
```

**提示：**

- 树中的节点数将在 `[0, 10^4]`的范围内。

- `-10^8 <= Node.val <= 10^8`

- 所有值 `Node.val` 是 **独一无二** 的。

- `-10^8 <= val <= 10^8`

- **保证** `val` 在原始BST中不存在。

官方原题：https://leetcode.cn/problems/insert-into-a-binary-search-tree

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0700-0799/0701.Insert%20into%20a%20Binary%20Search%20Tree/README.md

## 回放用例

输入：root = [4, 2, 7, 1, 3], val = 5

预期结果：[4, 2, 7, 1, 3, 5]

实际结果：[4, 2, 7, 1, 3, 5]

测试来源：本地设计的代表性用例；原文未提供该题独立输入

## Java 解法

```java
import java.util.*;

class Solution {
    // ① 返回插入后的子树根，父节点靠这个返回值接回孩子。
    public TreeNode insertIntoBST(TreeNode root, int val) {
        // ② 走到空位置就创建节点，它就是这一小棵树的新根。
        if (root == null) {
            return new TreeNode(val);
        }
        // ③ 按大小只改一侧；接回新根后，当前子树仍以 root 为根。
        if (val < root.val) {
            // 递归可能把空孩子变成新节点，必须用返回值接回。
            root.left = insertIntoBST(root.left, val);
        } else {
            root.right = insertIntoBST(root.right, val);
        }
        return root;
    }
}
```

## 来源与版本

递归与其他解法均逐字核对原文类体；只作内部类命名、static 与外层适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0701InsertIntoABinarySearchTree.java

源码指纹：3fde96c805a80e0603dd6be32c19a3a2d25ff0e14419fb533de737918eb8156f

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：16 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0701-insert-into-a-binary-search-tree/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
