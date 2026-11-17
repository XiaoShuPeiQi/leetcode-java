# 63 · 不同路径 II — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0063-unique-paths-ii/

动态规划 · Unique Paths II

## 题意

从网格左上角去右下角，只允许向右或向下移动。格子中的 1 表示障碍，0 表示可通行。求避开障碍的路径数。

官方原题：https://leetcode.cn/problems/unique-paths-ii/

## 课堂示例

输入：obstacleGrid = [[0, 0, 0], [0, 1, 0], [0, 0, 0]]

预期结果：2

实际结果：2

测试来源：本章实例走过程；与本地main和网页回放输入一致。采用力扣公开示例。

## 状态含义

dp[i][j] 表示到达该格的有效路径数；障碍为0。

## 状态变化

非障碍格累加存在的上邻格与左邻格；障碍直接跳过。

## Java 解法

```java
import java.util.*;

class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length, n = obstacleGrid[0].length;
        if (obstacleGrid[0][0] == 1) return 0;
        int[][] dp = new int[m][n];
        dp[0][0] = 1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (obstacleGrid[i][j] == 1 || (i == 0 && j == 0)) continue;
                if (i > 0) dp[i][j] += dp[i - 1][j];
                if (j > 0) dp[i][j] += dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }
}
```

## 来源与版本

算法与本章Java代码一致，仅增加包、独立main、static及运行适配；同题旧解法保留。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/dp/LC0063UniquePathsII.java

源码指纹：93699494f396f2f3e8864c845f5c2787c4d97e6df0f4a9033709fe5dd959da26

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：46 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0063-unique-paths-ii/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
