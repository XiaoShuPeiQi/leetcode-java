# 501 · 二叉搜索树中的众数 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0501-find-mode-in-binary-search-tree/

二叉树 · Find Mode In Binary Search Tree

## 题意

给你一个含重复值的二叉搜索树（BST）的根节点 `root` ，找出并返回 BST 中的所有 [众数](<https://baike.baidu.com/item/%E4%BC%97%E6%95%B0/44796>)（即，出现频率最高的元素）。

如果树中有不止一个众数，可以按 **任意顺序** 返回。

假定 BST 满足如下定义：

- 结点左子树中所含节点的值 **小于等于** 当前节点的值

- 结点右子树中所含节点的值 **大于等于** 当前节点的值

- 左子树和右子树都是二叉搜索树

**示例 1：**

![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0500-0599/0501.Find%20Mode%20in%20Binary%20Search%20Tree/images/mode-tree.jpg)

```text
输入：root = [1,null,2,2]
输出：[2]
```

**示例 2：**

```text
输入：root = [0]
输出：[0]
```

**提示：**

- 树中节点的数目在范围 `[1, 10^4]` 内

- `-10^5 <= Node.val <= 10^5`

**进阶：**你可以不使用额外的空间吗？（假设由递归产生的隐式调用栈的开销不被计算在内）

官方原题：https://leetcode.cn/problems/find-mode-in-binary-search-tree

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0500-0599/0501.Find%20Mode%20in%20Binary%20Search%20Tree/README.md

## 回放用例

输入：root = [2, 1, 2]

预期结果：[2]

实际结果：[2]

测试来源：本地设计的代表性用例；原文未提供该题独立输入

## Java 解法

```java
import java.util.*;

class Solution {
    private Integer previous;

    private int count;

    private int maximumCount;

    private List<Integer> modes;

    private void reset() {
        previous = null;
        count = 0;
        maximumCount = 0;
        modes = new ArrayList<>();
    }

    public int[] findMode(TreeNode root) {
        // 新一次遍历必须清空上次调用留下的计数和答案。
        reset();
        traversal(root);
        return toArray();
    }

    // ① 中序访问当前子树，连续计数和众数由共享字段维护。
    private void traversal(TreeNode node) {
        // ② 空节点不参与频次统计。
        if (node == null) {
            return;
        }
        // ③ 左→当前→右让相同值连续出现；record 只需统计连续段。
        traversal(node.left);
        record(node.val);
        traversal(node.right);
    }

    private void record(int value) {
        // 中序中相同值连续：相同就续段，不同就从 1 重新计数。
        if (previous != null && previous == value) {
            count++;
        } else {
            count = 1;
        }
        if (count > maximumCount) {
            maximumCount = count;
            // 出现更高频率，原来的候选都不再是众数。
            modes.clear();
            modes.add(value);
        // 达到相同最高频率，当前值也应列入答案。
        } else if (count == maximumCount) {
            modes.add(value);
        }
        previous = value;
    }

    private int[] toArray() {
        int[] result = new int[modes.size()];
        for (int i = 0; i < modes.size(); i++) {
            result[i] = modes.get(i);
        }
        return result;
    }
}
```

## 来源与版本

递归与其他解法均逐字核对原文类体；只作内部类命名、static 与外层适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0501FindModeInBinarySearchTree.java

源码指纹：db5024ea63c5f5faf249bd670203a2d58985136c8a54d45d37967ba5cc81c535

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：85 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0501-find-mode-in-binary-search-tree/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
