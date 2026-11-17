# 188 · 买卖股票的最佳时机 IV — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0188-best-time-to-buy-and-sell-stock-iv/

动态规划 · Best Time to Buy and Sell Stock IV

## 题意

根据每天的股价，至多做 k 笔完整的先买后卖交易。持仓结束后才能进行下一笔买入，求最大利润。

官方原题：https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-iv/

## 课堂示例

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
