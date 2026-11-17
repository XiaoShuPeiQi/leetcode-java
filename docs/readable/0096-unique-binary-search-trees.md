# 96 · 不同的二叉搜索树 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0096-unique-binary-search-trees/

动态规划 · Unique Binary Search Trees

## 题意

节点值使用 1 到 n，每个值恰使用一次。求可构造的不同二叉搜索树的数量。

官方原题：https://leetcode.cn/problems/unique-binary-search-trees/

## 课堂示例

输入：n = 3

预期结果：5

实际结果：5

测试来源：本章实例走过程；与本地main和网页回放输入一致。自选小规模示例。

## 状态含义

dp[i] 表示用 i 个有序且互不相同的值组成的二叉搜索树种数。

## 状态变化

根排在第 root 个时，左边 root-1 个、右边 i-root 个：dp[i] += dp[root-1]*dp[i-root]。

## Java 解法

```java
import java.util.*;

class Solution {
    public int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int root = 1; root <= i; root++) {
                dp[i] += dp[root - 1] * dp[i - root];
            }
        }
        return dp[n];
    }
}
```

## 来源与版本

算法与本章Java代码一致，仅增加包、独立main、static及运行适配；同题旧解法保留。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/dp/LC0096UniqueBinarySearchTrees.java

源码指纹：dcc0f29ce199e456560013442b2077fd63c0c5775a454d32ff31c2cae0ae49af

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：24 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0096-unique-binary-search-trees/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
