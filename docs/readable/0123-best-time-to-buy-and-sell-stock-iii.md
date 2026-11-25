# 123 · 买卖股票的最佳时机 III — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0123-best-time-to-buy-and-sell-stock-iii/

动态规划 · Best Time to Buy and Sell Stock III

## 题意

给定一个数组，它的第 `i` 个元素是一支给定的股票在第 `i` 天的价格。

设计一个算法来计算你所能获取的最大利润。你最多可以完成 **两笔** 交易。

**注意：**你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。

**示例 1:**

```text
输入：prices = [3,3,5,0,0,3,1,4]
输出：6
解释：在第 4 天（股票价格 = 0）的时候买入，在第 6 天（股票价格 = 3）的时候卖出，这笔交易所能获得利润 = 3-0 = 3 。
     随后，在第 7 天（股票价格 = 1）的时候买入，在第 8 天 （股票价格 = 4）的时候卖出，这笔交易所能获得利润 = 4-1 = 3 。
```

**示例 2：**

```text
输入：prices = [1,2,3,4,5]
输出：4
解释：在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
     注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。
     因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。
```

**示例 3：**

```text
输入：prices = [7,6,4,3,1]
输出：0
解释：在这个情况下, 没有交易完成, 所以最大利润为 0。
```

**示例 4：**

```text
输入：prices = [1]
输出：0
```

**提示：**

- `1 <= prices.length <= 10^5`

- `0 <= prices[i] <= 10^5`

官方原题：https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-iii

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0100-0199/0123.Best%20Time%20to%20Buy%20and%20Sell%20Stock%20III/README.md

## 回放用例

输入：prices = [3, 3, 5, 0, 0, 3, 1, 4]

预期结果：6

实际结果：6

测试来源：本章实例走过程；与本地main和网页回放输入一致。采用力扣公开示例。

## 状态含义

dp[i][0]=不操作；[1]=第一次持股；[2]=第一次卖出后的空仓；[3]=第二次持股；[4]=第二次卖出后的空仓。阶段表示“至多走到这里”，允许零收益的虚拟早期交易。

## 状态变化

奇数阶段买：max(昨日同阶段,昨日前阶段-price)；偶数阶段卖：max(昨日同阶段,昨日前阶段+price)。

## Java 解法

```java
import java.util.*;

class Solution {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        if (n == 0) return 0;
        int[][] dp = new int[n][5];
        dp[0][1] = dp[0][3] = -prices[0];
        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= 4; j++) {
                int delta = j % 2 == 1 ? -prices[i] : prices[i];
                dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - 1] + delta);
            }
        }
        return dp[n - 1][4];
    }
}
```

## 来源与版本

算法与本章Java代码一致，仅增加包、独立main、static及运行适配；同题旧解法保留。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/dp/LC0123BestTimetoBuyandSellStockIII.java

源码指纹：1a45ba54691bf6e0d4997624e881ab489a335a27684e50d56237d53c7f85f177

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：106 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0123-best-time-to-buy-and-sell-stock-iii/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
