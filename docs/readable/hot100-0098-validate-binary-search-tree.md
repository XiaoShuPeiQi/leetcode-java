# 98 · 验证二叉搜索树 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0098-validate-binary-search-tree/

Hot100 · 二叉树 · Validate Binary Search Tree

## 题意

判断一棵二叉树是否为严格的二叉搜索树。对每个节点，其整个左子树的值都更小，整个右子树的值都更大。

官方原题：https://leetcode.cn/problems/validate-binary-search-tree/

## 课堂示例

输入：root = [10, null, 15, 6]

预期结果：false

实际结果：false

测试来源：算法学习总笔记与项目已有 main

## 状态含义

previous 跨递归层共享，每次入口先清空。空子树返回 true。

## 状态变化

中序遍历时，每个节点值都必须大于前一个。

## Java 解法

```java
import java.util.*;

class Solution {
    private TreeNode previous;

    public boolean isValidBST(TreeNode root) {
        // 每次入口重置，避免上次调用的前驱影响本次判断。
        previous = null;
        return validate(root);
    }

    // ① 检查当前子树；previous 保存整个中序过程的上一个节点。
    private boolean validate(TreeNode node) {
        // ② 空树不会破坏严格递增，返回 true。
        if (node == null) {
            return true;
        }
        // ③ 先左、再比当前与前驱、最后右；任一步失败就停止。
        if (!validate(node.left)) {
            return false;
        }
        // 必须严格递增；相等也不是本题的合法 BST。
        if (previous != null && node.val <= previous.val) {
            return false;
        }
        previous = node;
        return validate(node.right);
    }
}
```

## 来源与版本

沿用总笔记及仓库中本题的主解。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0098ValidateBinarySearchTree.java

源码指纹：46ed1e1d58ac59edd60ba4939b0de07c6ab86b393a31a1fd3cd5719ecbbae95f

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：30 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0098-validate-binary-search-tree/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
