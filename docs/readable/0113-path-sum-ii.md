# 113 · 路径总和 II（关联补充） — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0113-path-sum-ii/

二叉树 · Path Sum II

## 题意

给你二叉树的根节点 `root` 和一个整数目标和 `targetSum` ，找出所有 **从根节点到叶子节点** 路径总和等于给定目标和的路径。

**叶子节点** 是指没有子节点的节点。

**示例 1：**

![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0100-0199/0113.Path%20Sum%20II/images/pathsumii1.jpg)

```text
输入：root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
输出：[[5,4,11,2],[5,8,4,5]]
```

**示例 2：**

![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0100-0199/0113.Path%20Sum%20II/images/pathsum2.jpg)

```text
输入：root = [1,2,3], targetSum = 5
输出：[]
```

**示例 3：**

```text
输入：root = [1,2], targetSum = 0
输出：[]
```

**提示：**

- 树中节点总数在范围 `[0, 5000]` 内

- `-1000 <= Node.val <= 1000`

- `-1000 <= targetSum <= 1000`

官方原题：https://leetcode.cn/problems/path-sum-ii

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0100-0199/0113.Path%20Sum%20II/README.md

## 回放用例

输入：root = [4, 2, 6, 1, 3, 5, 7], targetSum = 7

预期结果：[[4, 2, 1]]

实际结果：[[4, 2, 1]]

测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）

## Java 解法

```java
import java.util.*;

class Solution {
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> result = new ArrayList<>();
        traversal(root, targetSum, new ArrayList<>(), result);
        return result;
    }

    // ① remaining 尚未扣当前值；path 是共享的当前路径，结果要复制。
    private void traversal(TreeNode node, long remaining,
            List<Integer> path, List<List<Integer>> result) {
        // ② 空节点没有路径可继续，直接返回。
        if (node == null) {
            return;
        }
        // ③ 加自己、扣目标，到合格叶子存快照；两边搜索完后撤销当前节点。
        path.add(node.val);
        long nextRemaining = remaining - node.val;
        if (node.left == null && node.right == null && nextRemaining == 0) {
            // 保存副本；直接放 path，后续撤销会把已有答案改掉。
            result.add(new ArrayList<>(path));
        }
        traversal(node.left, nextRemaining, path, result);
        traversal(node.right, nextRemaining, path, result);
        // path 被各层共享，返回前删掉自己，恢复父路径。
        path.remove(path.size() - 1);
    }
}
```

## 来源与版本

递归与其他解法均逐字核对原文类体；只作内部类命名、static 与外层适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0113PathSumII.java

源码指纹：897ff3d8151fef1b6f1d6e60d8e24ad72ba912dd59712659396d8183b859bb5e

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：108 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0113-path-sum-ii/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
