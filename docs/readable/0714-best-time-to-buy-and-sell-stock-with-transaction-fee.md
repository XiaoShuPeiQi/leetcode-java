# 714 · 买卖股票的最佳时机含手续费 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0714-best-time-to-buy-and-sell-stock-with-transaction-fee/

动态规划 · Best Time to Buy and Sell Stock with Transaction Fee

## 题意

根据每天的股价做多笔交易，每笔完整买卖扣一次手续费，至多同时持有一股。求最大净利润。

官方原题：https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/

## 课堂示例

输入：prices = [1, 3, 2, 8, 4, 9], fee = 2

预期结果：8

实际结果：8

测试来源：本章实例走过程；与本地main和网页回放输入一致。采用力扣公开示例。

## 状态含义

dp[i][0]=第 i 天结束持股的最大净收益；dp[i][1]=空仓的最大净收益。

## 状态变化

持股=max(昨日持股,昨日空仓-price)；空仓=max(昨日空仓,昨日持股+price-fee)。

## Java 解法

```java
import java.util.*;

class Solution {
    public int maxProfit(int[] prices, int fee) {
        int n = prices.length;
        if (n == 0) return 0;
        int[][] dp = new int[n][2];
        dp[0][0] = -prices[0];
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] - prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] + prices[i] - fee);
        }
        return dp[n - 1][1];
    }
}
```

## 来源与版本

算法与本章Java代码一致，仅增加包、独立main、static及运行适配；同题旧解法保留。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/dp/LC0714BestTimetoBuyandSellStockwithTransactionFee.java

源码指纹：c090e9b3d4459367028e7bd41eb7c3d4caa82b4c1ec023ab7a980c84be23c551

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：23 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0714-best-time-to-buy-and-sell-stock-with-transaction-fee/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
