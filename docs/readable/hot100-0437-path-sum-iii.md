# 437 · 路径总和 III — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0437-path-sum-iii/

Hot100 · 二叉树 · Path Sum III

## 题意

给定一个二叉树的根节点 `root` ，和一个整数 `targetSum` ，求该二叉树里节点值之和等于 `targetSum` 的 **路径** 的数目。

**路径** 不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。

**示例 1：**

![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0400-0499/0437.Path%20Sum%20III/images/pathsum3-1-tree.jpg)

```text
输入：root = [10,5,-3,3,2,null,11,3,-2,null,1], targetSum = 8
输出：3
解释：和等于 8 的路径有 3 条，如图所示。
```

**示例 2：**

```text
输入：root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
输出：3
```

**提示:**

- 二叉树的节点个数的范围是 `[0,1000]`

- `-10^9 <= Node.val <= 10^9`

- `-1000 <= targetSum <= 1000`

官方原题：https://leetcode.cn/problems/path-sum-iii

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0400-0499/0437.Path%20Sum%20III/README.md

## 回放用例

输入：root=[10,5,-3,3,2,null,11,3,-2,null,1], targetSum=8

预期结果：3

实际结果：3

测试来源：力扣公开示例

## 状态含义

Map 只含当前分支的前缀。sum 用 long，res 累加当前点结尾的路径数和两个子树的结果。

## 状态变化

沿根到当前点计数前缀和，退出分支时撤销本层记录。

## Java 解法

```java
import java.util.*;

class Solution {
    public int pathSum(TreeNode root, int targetSum) {
        Map<Long, Integer> count = new HashMap<>();
        count.put(0L, 1);
        return traversal(root, targetSum, 0L, count);
    }

    private int traversal(TreeNode node, int targetSum, long sum, Map<Long, Integer> count) {
        if (node == null) {
            return 0;
        }
        sum += node.val;
        int res = count.getOrDefault(sum - targetSum, 0);
        count.put(sum, count.getOrDefault(sum, 0) + 1);
        res += traversal(node.left, targetSum, sum, count);
        res += traversal(node.right, targetSum, sum, count);
        // 离开当前分支，撤销这一层的累计和。
        int frequency = count.get(sum);
        if (frequency == 1) {
            count.remove(sum);
        } else {
            count.put(sum, frequency - 1);
        }
        return res;
    }
}
```

## 来源与版本

学习扩展。按本题条件选择主解。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0437PathSumIii.java

源码指纹：b9ee5a8913e258c82586623a6642a725e43b14a5c5f4ef6856cc362cb432c3e9

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：153 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0437-path-sum-iii/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
