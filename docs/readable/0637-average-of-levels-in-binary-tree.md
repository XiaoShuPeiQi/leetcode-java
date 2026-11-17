# 637 · 二叉树的层平均值 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0637-average-of-levels-in-binary-tree/

二叉树 · Average Of Levels In Binary Tree

## 题意

对二叉树逐层计算节点值的算术平均数，按由浅到深的顺序返回。

官方原题：https://leetcode.cn/problems/average-of-levels-in-binary-tree/

## 课堂示例

输入：root = [4, 2, 6, 1, 3, 5, 7]

预期结果：[4.0, 4.0, 4.0]

实际结果：[4.0, 4.0, 4.0]

测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）

## Java 解法

```java
import java.util.*;

class Solution {
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> sums = new ArrayList<>();
        List<Integer> counts = new ArrayList<>();
        traversal(root, 0, sums, counts);
        for (int i = 0; i < sums.size(); i++) {
            sums.set(i, sums.get(i) / counts.get(i));
        }
        return sums;
    }

    // ① depth 标记层；sums 和 counts 分别记录各层总和、数量。
    private void traversal(TreeNode node, int depth,
            List<Double> sums, List<Integer> counts) {
        // ② 空节点不参与统计。
        if (node == null) {
            return;
        }
        // ③ 新层先建统计位置；当前值和数量更新后，再处理孩子。
        if (depth == sums.size()) {
            // 总和用 double，避免一整层用 int 相加溢出。
            sums.add(0.0);
            counts.add(0);
        }
        sums.set(depth, sums.get(depth) + node.val);
        counts.set(depth, counts.get(depth) + 1);
        traversal(node.left, depth + 1, sums, counts);
        traversal(node.right, depth + 1, sums, counts);
    }
}
```

## 来源与版本

递归与其他解法均逐字核对原文类体；只作内部类命名、static 与外层适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0637AverageOfLevelsInBinaryTree.java

源码指纹：2786b3aa85124ff24a20c9e43661866bdf038360e1131c666d08a51b4524f11d

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：114 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0637-average-of-levels-in-binary-tree/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
