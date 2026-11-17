# 112 · 路径总和 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0112-path-sum/

二叉树 · Path Sum

## 题意

判断二叉树中是否存在一条从根到叶的路径，使沿途节点值相加等于 targetSum。

官方原题：https://leetcode.cn/problems/path-sum/

## 课堂示例

输入：root = [4, 2, 6, 1, 3, 5, 7], targetSum = 7

预期结果：true

实际结果：true

测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）

## Java 解法

```java
import java.util.*;

class Solution {
    public boolean hasPathSum(TreeNode root, int targetSum) {
        return exists(root, targetSum);
    }

    // ① remaining 是当前节点到叶子还需凑出的和，尚未扣当前值。
    private boolean exists(TreeNode node, long remaining) {
        // ② 空树没有根到叶路径，所以返回 false。
        if (node == null) {
            return false;
        }
        // ③ 到叶子才判成功；非叶子扣掉自己，把剩余任务交给任一孩子。
        if (node.left == null && node.right == null) {
            return remaining == node.val;
        }
        // 数字按值传递，每个孩子拿相同余量，不需要手动恢复。
        long nextRemaining = remaining - node.val;
        // 任一侧找到即可；|| 会在左侧成功时停止搜索。
        return exists(node.left, nextRemaining) || exists(node.right, nextRemaining);
    }
}
```

## 来源与版本

递归与其他解法均逐字核对原文类体；只作内部类命名、static 与外层适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0112PathSum.java

源码指纹：a99de7037a3d66626d2ad3d4b36fda35926b4e019f04de53bdbbeadb477c74e6

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：20 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0112-path-sum/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
