# 701 · 二叉搜索树中的插入操作 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0701-insert-into-a-binary-search-tree/

二叉树 · Insert Into ABinary Search Tree

## 题意

把新值 val 插入二叉搜索树，返回插入后的根节点。输入保证树中没有该值，任一合法结果均可。

官方原题：https://leetcode.cn/problems/insert-into-a-binary-search-tree/

## 课堂示例

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
