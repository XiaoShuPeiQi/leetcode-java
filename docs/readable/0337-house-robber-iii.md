# 337 · 打家劫舍 III — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0337-house-robber-iii/

动态规划 · House Robber III

## 题意

房屋连成一棵二叉树。选择一些房屋取钱，父子不能同时选。求可取的最大金额。

官方原题：https://leetcode.cn/problems/house-robber-iii/

## 课堂示例

输入：root = [3, 2, 3, null, 3, null, 1]

预期结果：7

实际结果：7

测试来源：本章实例走过程；与本地main和网页回放输入一致。采用力扣公开示例。

## 状态含义

traverse(node) 返回 int[2]：下标0是不偷该节点时整棵子树的最大金额，下标1是偷该节点时的最大金额。

## 状态变化

不偷 node：左右孩子各取偷或不偷的最大值。偷 node：node.val+left[0]+right[0]，孩子必须不偷。

## Java 解法

```java
import java.util.*;

class Solution {
    public int rob(TreeNode root) {
        int[] result = traverse(root);
        return Math.max(result[0], result[1]);
    }

    private int[] traverse(TreeNode node) {
        if (node == null) return new int[]{0, 0};
        int[] left = traverse(node.left);
        int[] right = traverse(node.right);
        int skip = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        int take = node.val + left[0] + right[0]; // 偷父节点，孩子必须不偷
        return new int[]{skip, take};
    }
}
```

## 来源与版本

算法与本章Java代码一致，仅增加包、独立main、static及运行适配；同题旧解法保留。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/dp/LC0337HouseRobberIII.java

源码指纹：9f238472d89d6d91053b03ca5ba8fdefb4df3c6f46b7ab66c9505823673343b2

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：62 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0337-house-robber-iii/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
