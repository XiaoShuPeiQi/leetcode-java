# 518 · 零钱兑换 II — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0518-coin-change-ii/

动态规划 · Coin Change II

## 题意

用不限数量的各面额硬币凑出 amount，求不同组合的数量。只改变硬币选择顺序视为同一组合。

官方原题：https://leetcode.cn/problems/coin-change-ii/

## 课堂示例

输入：amount = 5, coins = [1, 2, 5]

预期结果：4

实际结果：4

测试来源：本章实例走过程；与本地main和网页回放输入一致。采用力扣公开示例。

## 状态含义

dp[j] 表示只使用已处理种类的硬币，凑成金额 j 的组合数。

## 状态变化

dp[j] += dp[j-coin]；当前种类可重复使用，来源允许已经包含当前硬币。

## Java 解法

```java
import java.util.*;

class Solution {
    public int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int coin : coins) {
            for (int j = coin; j <= amount; j++) {
                dp[j] += dp[j - coin]; // 正序允许重复用当前硬币
            }
        }
        return dp[amount];
    }
}
```

## 来源与版本

算法与本章Java代码一致，仅增加包、独立main、static及运行适配；同题旧解法保留。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/dp/LC0518CoinChangeII.java

源码指纹：154620b6dd62da42058dbabd45cc25589b27accbc60db0079a5c4caf7943830a

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：32 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0518-coin-change-ii/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
