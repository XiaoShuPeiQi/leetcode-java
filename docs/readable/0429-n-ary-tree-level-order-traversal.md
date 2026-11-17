# 429 · N 叉树的层序遍历 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0429-n-ary-tree-level-order-traversal/

二叉树 · NAry Tree Level Order Traversal

## 题意

从根开始逐层读取 N 叉树的节点值。同层按孩子在输入中的先后顺序排列，每层返回一个列表。

官方原题：https://leetcode.cn/problems/n-ary-tree-level-order-traversal/

## 课堂示例

输入：N 叉树：1 的孩子为 3、2、4；3 的孩子为 5、6

预期结果：[[1], [3, 2, 4], [5, 6]]

实际结果：[[1], [3, 2, 4], [5, 6]]

测试来源：本地设计的代表性用例；原文未提供该题独立输入

## Java 解法

```java
import java.util.*;

class Solution {
    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> result = new ArrayList<>();
        traversal(root, 0, result);
        return result;
    }

    // ① depth 决定放在哪一层；result 保存各层节点。
    private void traversal(Node node, int depth, List<List<Integer>> result) {
        // ② 空节点没有内容，直接返回。
        if (node == null) {
            return;
        }
        // ③ 记录当前节点，再按 children 的顺序处理下一层。
        if (depth == result.size()) {
            result.add(new ArrayList<>());
        }
        result.get(depth).add(node.val);
        if (node.children != null) {
            // 二叉树的两次递归，在这里换成遍历所有孩子。
            for (Node child : node.children) {
                traversal(child, depth + 1, result);
            }
        }
    }
}
```

## 来源与版本

递归与其他解法均逐字核对原文类体；只作内部类命名、static 与外层适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0429NAryTreeLevelOrderTraversal.java

源码指纹：59fb343b40d247aeb805a2bdcd64c3a0e66588a644343e1789d0f85cc5acd630

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：66 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0429-n-ary-tree-level-order-traversal/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
