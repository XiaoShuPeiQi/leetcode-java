# 515 · 在每个树行中找最大值 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0515-find-largest-value-in-each-tree-row/

二叉树 · Find Largest Value In Each Tree Row

## 题意

给定一棵二叉树的根节点 `root` ，请找出该二叉树中每一层的最大值。

**示例1：**

![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0500-0599/0515.Find%20Largest%20Value%20in%20Each%20Tree%20Row/images/largest_e1.jpg)

```text
输入: root = [1,3,2,5,3,null,9]
输出: [1,3,9]
```

**示例2：**

```text
输入: root = [1,2,3]
输出: [1,3]
```

**提示：**

- 二叉树的节点个数的范围是 `[0,10^4]`

- `-2^(31) <= Node.val <= 2^(31) - 1`

官方原题：https://leetcode.cn/problems/find-largest-value-in-each-tree-row

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0500-0599/0515.Find%20Largest%20Value%20in%20Each%20Tree%20Row/README.md

## 回放用例

输入：root = [4, 2, 6, 1, 3, 5, 7]

预期结果：[4, 6, 7]

实际结果：[4, 6, 7]

测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）

## Java 解法

```java
import java.util.*;

class Solution {
    public List<Integer> largestValues(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        traversal(root, 0, result);
        return result;
    }

    // ① depth 标记层；result[depth] 保存这层目前的最大值。
    private void traversal(TreeNode node, int depth, List<Integer> result) {
        // ② 空节点不参与最大值比较。
        if (node == null) {
            return;
        }
        // ③ 新层用真实节点值初始化，避免全负数时误取 0。
        if (depth == result.size()) {
            result.add(node.val);
        } else {
            result.set(depth, Math.max(result.get(depth), node.val));
        }
        traversal(node.left, depth + 1, result);
        traversal(node.right, depth + 1, result);
    }
}
```

## 来源与版本

递归与其他解法均逐字核对原文类体；只作内部类命名、static 与外层适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0515FindLargestValueInEachTreeRow.java

源码指纹：8564de5a3db629d96f8afe118373fed055030c66d27f91fbdb3a47343969ecc7

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：93 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0515-find-largest-value-in-each-tree-row/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
