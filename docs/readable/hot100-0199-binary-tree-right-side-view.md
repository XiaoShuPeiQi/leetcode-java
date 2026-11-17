# 199 · 二叉树的右视图 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0199-binary-tree-right-side-view/

Hot100 · 二叉树 · Binary Tree Right Side View

## 题意

从二叉树右侧看，每个深度只看到最靠右的节点。按从根到最深层的顺序返回它们的值。

官方原题：https://leetcode.cn/problems/binary-tree-right-side-view/

## 课堂示例

输入：root = [4, 2, 6, 1, 3, 5, 7]

预期结果：[4, 6, 7]

实际结果：[4, 6, 7]

测试来源：算法学习总笔记与项目已有 main

## 状态含义

depth 等于结果长度时记录。右侧没有足够深时，左侧仍可能补上新层。

## 状态变化

每层先到最右侧，只记录第一次到达的节点。

## Java 解法

```java
import java.util.*;

class Solution {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        traversal(root, 0, result);
        return result;
    }

    // ① depth 标记层；result 每层只保留最右边一个值。
    private void traversal(TreeNode node, int depth, List<Integer> result) {
        // ② 空节点看不到，直接返回。
        if (node == null) {
            return;
        }
        // ③ 先右后左，所以新深度的第一个节点就是最右节点。
        if (depth == result.size()) {
            result.add(node.val);
        }
        // 右侧优先占位；右侧缺失时，左子树仍能补上更深层。
        traversal(node.right, depth + 1, result);
        traversal(node.left, depth + 1, result);
    }
}
```

## 来源与版本

沿用总笔记及仓库中本题的主解。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0199BinaryTreeRightSideView.java

源码指纹：324eef2cdb899a7c8f8ccb8d3e7f542e7de4887ea50d2dd03378c67741385258

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：89 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0199-binary-tree-right-side-view/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
