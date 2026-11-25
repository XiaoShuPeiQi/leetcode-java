# 188 · 买卖股票的最佳时机 IV — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0188-best-time-to-buy-and-sell-stock-iv/

动态规划 · Best Time to Buy and Sell Stock IV

## 题意

给你一个整数数组 `prices` 和一个整数 `k` ，其中 `prices[i]` 是某支给定的股票在第 `i` 天的价格。

设计一个算法来计算你所能获取的最大利润。你最多可以完成 `k` 笔交易。也就是说，你最多可以买 `k` 次，卖 `k` 次。

**注意：**你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。

**示例 1：**

```text
输入：k = 2, prices = [2,4,1]
输出：2
解释：在第 1 天 (股票价格 = 2) 的时候买入，在第 2 天 (股票价格 = 4) 的时候卖出，这笔交易所能获得利润 = 4-2 = 2 。
```

**示例 2：**

```text
输入：k = 2, prices = [3,2,6,5,0,3]
输出：7
解释：在第 2 天 (股票价格 = 2) 的时候买入，在第 3 天 (股票价格 = 6) 的时候卖出, 这笔交易所能获得利润 = 6-2 = 4 。
     随后，在第 5 天 (股票价格 = 0) 的时候买入，在第 6 天 (股票价格 = 3) 的时候卖出, 这笔交易所能获得利润 = 3-0 = 3 。
```

**提示：**

- `1 <= k <= 100`

- `1 <= prices.length <= 1000`

- `0 <= prices[i] <= 1000`

官方原题：https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-iv

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0100-0199/0188.Best%20Time%20to%20Buy%20and%20Sell%20Stock%20IV/README.md

## 回放用例

输入：k = 2, prices = [3, 2, 6, 5, 0, 3]

预期结果：7

实际结果：7

测试来源：本章实例走过程；与本地main和网页回放输入一致。采用力扣公开示例。

## 状态含义

dp[i][j] 为第 i 天结束时阶段 j 的最大收益；0为空操作，奇数持股，偶数空仓。

## 状态变化

奇数 j 从 j-1 阶段买入，偶数 j 从 j-1 阶段卖出；都与昨日同阶段取最大。

## Java 解法

```java
import java.util.*;

class Solution {
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;
        if (k == 0 || n < 2) return 0;
        k = Math.min(k, n / 2);
        int[][] dp = new int[n][2 * k + 1];
        for (int j = 1; j <= 2 * k; j += 2) dp[0][j] = -prices[0];
        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= 2 * k; j++) {
                int delta = j % 2 == 1 ? -prices[i] : prices[i];
                dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - 1] + delta);
            }
        }
        return dp[n - 1][2 * k];
    }
}
```

## 来源与版本

算法与本章Java代码一致，仅增加包、独立main、static及运行适配；同题旧解法保留。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/dp/LC0188BestTimetoBuyandSellStockIV.java

源码指纹：abdcb58559b9b789ab6f4296a0372eb7d2a8f90efe674ccb47d282745dd6f616

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：79 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0188-best-time-to-buy-and-sell-stock-iv/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
