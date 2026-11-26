# Hot100 第 92 题 · 力扣 64 · 最小路径和 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0064-minimum-path-sum/

Hot100 · 多维动态规划 · Minimum Path Sum

## 题意

给定一个包含非负整数的 `m x n` 网格 `grid` ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。

**说明：**每次只能向下或者向右移动一步。

**示例 1：**

```text
输入：grid = [[1,3,1],[1,5,1],[4,2,1]]
输出：7
解释：因为路径 1→3→1→1→1 的总和最小。
```

**示例 2：**

```text
输入：grid = [[1,2,3],[4,5,6]]
输出：12
```

**提示：**

- `m == grid.length`
- `n == grid[i].length`
- `1 <= m, n <= 200`
- `0 <= grid[i][j] <= 200`

官方原题：https://leetcode.cn/problems/minimum-path-sum/

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0000-0099/0064.Minimum%20Path%20Sum/README.md

## 回放用例

输入：grid = [[1, 3, 1], [1, 5, 1], [4, 2, 1]]

预期结果：7

实际结果：7

测试来源：力扣官方示例，与本节推演一致。

## 解法

原地二维动态规划

## 状态含义

把累计最小路径和写回 grid。处理过的格子存的是从起点到它的最小路径和。首行只能从左边来，首列只能从上面来。

## 状态变化

从上方和左方的累计路径和中选较小的，再加上当前格子的数字。

## Java 解法

```java
class Solution {
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        for (int j = 1; j < n; j++) {
            grid[0][j] += grid[0][j - 1];
        }
        for (int i = 1; i < m; i++) {
            grid[i][0] += grid[i - 1][0];
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                grid[i][j] += Math.min(grid[i - 1][j], grid[i][j - 1]);
            }
        }
        return grid[m - 1][n - 1];
    }
}
```

## 来源与版本

依据代码随想录的解法思路整理 Java 版。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/dp/LC0064MinimumPathSum.java

源码指纹：b45696b5cdef30ded84c7ba4c2573bc0df5d06d0966353b79f39e72bdf264ed9

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcnWKJA7aqSOoNgYChyv8VtVb（外部文档可能需要登录，本文仅包含本页已发布的内容。）

代码随想录 Java 解法：https://programmercarl.com/hot100/0064.minimum-path-sum.html

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：28 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0064-minimum-path-sum/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
