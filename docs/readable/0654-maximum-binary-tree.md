# 654 · 最大二叉树 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0654-maximum-binary-tree/

二叉树 · Maximum Binary Tree

## 题意

给定一个不重复的整数数组 `nums` 。 **最大二叉树** 可以用下面的算法从 `nums` 递归地构建:

1. 创建一个根节点，其值为 `nums` 中的最大值。

2. 递归地在最大值 **左边** 的 **子数组前缀上** 构建左子树。

3. 递归地在最大值 **右边** 的 **子数组后缀上** 构建右子树。

返回 *`nums` 构建的* ***最大二叉树*** 。

**示例 1：**

![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0600-0699/0654.Maximum%20Binary%20Tree/images/tree1.jpg)

```text
输入：nums = [3,2,1,6,0,5]
输出：[6,3,5,null,2,0,null,null,1]
解释：递归调用如下所示：
- [3,2,1,6,0,5] 中的最大值是 6 ，左边部分是 [3,2,1] ，右边部分是 [0,5] 。
    - [3,2,1] 中的最大值是 3 ，左边部分是 [] ，右边部分是 [2,1] 。
        - 空数组，无子节点。
        - [2,1] 中的最大值是 2 ，左边部分是 [] ，右边部分是 [1] 。
            - 空数组，无子节点。
            - 只有一个元素，所以子节点是一个值为 1 的节点。
    - [0,5] 中的最大值是 5 ，左边部分是 [0] ，右边部分是 [] 。
        - 只有一个元素，所以子节点是一个值为 0 的节点。
        - 空数组，无子节点。
```

**示例 2：**

![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0600-0699/0654.Maximum%20Binary%20Tree/images/tree2.jpg)

```text
输入：nums = [3,2,1]
输出：[3,null,2,null,1]
```

**提示：**

- `1 <= nums.length <= 1000`

- `0 <= nums[i] <= 1000`

- `nums` 中的所有整数 **互不相同**

官方原题：https://leetcode.cn/problems/maximum-binary-tree

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0600-0699/0654.Maximum%20Binary%20Tree/README.md

## 回放用例

输入：nums = [3, 2, 1, 6, 0, 5]

预期结果：[6, 3, 5, null, 2, 0, null, null, 1]

实际结果：[6, 3, 5, null, 2, 0, null, null, 1]

测试来源：本地设计的代表性用例；原文未提供该题独立输入

## Java 解法

```java
import java.util.*;

class Solution {
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return build(nums, 0, nums.length);
    }

    // ① 返回 nums[left, right) 构成的最大二叉树根。
    private TreeNode build(int[] nums, int left, int right) {
        // ② 空区间没有节点，返回 null。
        if (left == right) {
            return null;
        }
        // ③ 找区间最大值建根，再递归两侧；根下标不能重复进入子区间。
        int maximumIndex = left;
        for (int i = left + 1; i < right; i++) {
            if (nums[i] > nums[maximumIndex]) {
                maximumIndex = i;
            }
        }
        TreeNode root = new TreeNode(nums[maximumIndex]);
        root.left = build(nums, left, maximumIndex);
        root.right = build(nums, maximumIndex + 1, right);
        return root;
    }
}
```

## 来源与版本

递归与其他解法均逐字核对原文类体；只作内部类命名、static 与外层适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0654MaximumBinaryTree.java

源码指纹：fd64a5826408b67218fb6e71b9200a8377847ea36dfd4f2df76a4f78545fc270

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：105 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0654-maximum-binary-tree/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
