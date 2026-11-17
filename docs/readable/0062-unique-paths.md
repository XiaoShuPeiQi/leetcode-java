# 62 · 不同路径 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0062-unique-paths/

动态规划 · Unique Paths

## 题意

机器人从 m×n 网格左上角去右下角，每次只向右或向下移动一格。求不同路径总数。

官方原题：https://leetcode.cn/problems/unique-paths/

## 课堂示例

输入：m = 3, n = 3

预期结果：6

实际结果：6

测试来源：本章实例走过程；与本地main和网页回放输入一致。自选小规模示例。

## 状态含义

dp[i][j] 表示从左上角到第 i 行第 j 列的路径数；下标从0开始。

## 状态变化

dp[i][j]=dp[i-1][j]+dp[i][j-1]。

## Java 解法

```java
import java.util.*;

class Solution {
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) dp[i][0] = 1;
        for (int j = 0; j < n; j++) dp[0][j] = 1;
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }
}
```

## 来源与版本

算法与本章Java代码一致，仅增加包、独立main、static及运行适配；同题旧解法保留。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/dp/LC0062UniquePaths.java

源码指纹：01a75a12392e96651502fe0391b066b79a51531ac26fb56a4ca8f41b0aa09e04

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：19 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0062-unique-paths/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
