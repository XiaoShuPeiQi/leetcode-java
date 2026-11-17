# 746 · 使用最小花费爬楼梯 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0746-min-cost-climbing-stairs/

动态规划 · Min Cost Climbing Stairs

## 题意

每踩到一个台阶需要支付 cost 中对应的费用，之后可向上走一级或两级。可以从下标 0 或 1 开始，求到达数组末端之外的楼顶所需的最少费用。

官方原题：https://leetcode.cn/problems/min-cost-climbing-stairs/

## 课堂示例

输入：cost = [10, 15, 20]

预期结果：15

实际结果：15

测试来源：本章实例走过程；与本地main和网页回放输入一致。采用力扣公开示例。

## 状态含义

dp[i] 表示到达下标 i 的位置所需的最小花费；此时尚未支付 cost[i]。楼顶是 n。

## 状态变化

dp[i]=min(dp[i-1]+cost[i-1], dp[i-2]+cost[i-2])。从哪一阶离开，就支付哪一阶费用。

## Java 解法

```java
import java.util.*;

class Solution {
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        int[] dp = new int[n + 1];
        for (int i = 2; i <= n; i++) {
            dp[i] = Math.min(dp[i - 1] + cost[i - 1],
                    dp[i - 2] + cost[i - 2]); // 付费发生在离开台阶时
        }
        return dp[n];
    }
}
```

## 来源与版本

算法与本章Java代码一致，仅增加包、独立main、static及运行适配；同题旧解法保留。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/dp/LC0746MinCostClimbingStairs.java

源码指纹：9b378c988663aa7d77eea1763c5a4d13f0d94731ab5285f38067d314f232704a

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：10 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0746-min-cost-climbing-stairs/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
