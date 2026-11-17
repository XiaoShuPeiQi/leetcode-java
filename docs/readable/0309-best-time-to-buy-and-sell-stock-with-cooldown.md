# 309 · 买卖股票的最佳时机含冷冻期 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0309-best-time-to-buy-and-sell-stock-with-cooldown/

动态规划 · Best Time to Buy and Sell Stock with Cooldown

## 题意

根据每天的股价做多笔买卖，最多同时持有一股。每次卖出后的下一天不能买入，求最大利润。

官方原题：https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-with-cooldown/

## 课堂示例

输入：prices = [1, 2, 3, 0, 2]

预期结果：3

实际结果：3

测试来源：本章实例走过程；与本地main和网页回放输入一致。采用力扣公开示例。

## 状态含义

dp[i][0]=持股；[1]=空仓且今天没有卖出（次日可买）；[2]=今天刚卖出。

## 状态变化

持股=max(昨日持股,昨日休息-price)；休息=max(昨日休息,昨日刚卖)；刚卖=昨日持股+price。

## Java 解法

```java
import java.util.*;

class Solution {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        if (n == 0) return 0;
        int[][] dp = new int[n][3];
        dp[0][0] = -prices[0];
        dp[0][2] = Integer.MIN_VALUE / 2;
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] - prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][2]);
            dp[i][2] = dp[i - 1][0] + prices[i];
        }
        return Math.max(dp[n - 1][1], dp[n - 1][2]);
    }
}
```

## 来源与版本

算法与本章Java代码一致，仅增加包、独立main、static及运行适配；同题旧解法保留。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/dp/LC0309BestTimetoBuyandSellStockwithCooldown.java

源码指纹：a6e07c2e06f0e85c772dd98ab25dec575699cc9e38891a5cb93aa58dc0fd69f1

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：25 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0309-best-time-to-buy-and-sell-stock-with-cooldown/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
