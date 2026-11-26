# Hot100 第 91 题 · 力扣 62 · 不同路径 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0062-unique-paths/

Hot100 · 多维动态规划 · Unique Paths

## 题意

一个机器人位于一个 `m x n` 网格的左上角 （起始点在下图中标记为 “Start” ）。

机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。

问总共有多少条不同的路径？

**示例 1：**

![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0000-0099/0062.Unique%20Paths/images/1697422740-adxmsI-image.png)

```text
输入：m = 3, n = 7
输出：28
```

**示例 2：**

```text
输入：m = 3, n = 2
输出：3
解释：
从左上角开始，总共有 3 条路径可以到达右下角。
1. 向右 -> 向下 -> 向下
2. 向下 -> 向下 -> 向右
3. 向下 -> 向右 -> 向下
```

**示例 3：**

```text
输入：m = 7, n = 3
输出：28
```

**示例 4：**

```text
输入：m = 3, n = 3
输出：6
```

**提示：**

- `1 <= m, n <= 100`

- 题目数据保证答案小于等于 `2 * 10^9`

官方原题：https://leetcode.cn/problems/unique-paths

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0000-0099/0062.Unique%20Paths/README.md

## 回放用例

输入：m = 3, n = 3

预期结果：6

实际结果：6

测试来源：算法学习总笔记的原推演，与本地 Java 主程序一致。

## 解法

动态规划

## 状态含义

dp[i][j] 表示从左上角到第 i 行第 j 列的路径数；下标从0开始。

## 状态变化

每个格子的路径数等于上方与左方路径数之和。

## Java 解法

```java
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

誊写算法学习总笔记的主解和原示例。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/dp/LC0062UniquePaths.java

源码指纹：01a75a12392e96651502fe0391b066b79a51531ac26fb56a4ca8f41b0aa09e04

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcng4kjDX7uGxobCTfLn7w3Dc（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：19 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0062-unique-paths/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
