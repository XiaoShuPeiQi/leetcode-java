# 198 · 打家劫舍 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0198-house-robber/

动态规划 · House Robber

## 题意

一排房屋各有一定金额。选择一些房屋取钱，相邻的不能同时选。求可取的最大金额。

官方原题：https://leetcode.cn/problems/house-robber/

## 课堂示例

输入：nums = [2, 7, 9, 3, 1]

预期结果：12

实际结果：12

测试来源：本章实例走过程；与本地main和网页回放输入一致。采用力扣公开示例。

## 状态含义

dp[i] 表示只考虑前 i 间房，能够取得的最大金额。nums 的当前下标是 i-1。

## 状态变化

dp[i]=max(dp[i-1],dp[i-2]+nums[i-1])。

## Java 解法

```java
import java.util.*;

class Solution {
    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;
        int[] dp = new int[n + 1];
        dp[1] = nums[0];
        for (int i = 2; i <= n; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i - 1]);
        }
        return dp[n];
    }
}
```

## 来源与版本

算法与本章Java代码一致，仅增加包、独立main、static及运行适配；同题旧解法保留。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/dp/LC0198HouseRobber.java

源码指纹：baade9866965e977e642f36a57c1b20a591840119a859c23f0bb990fbf91554b

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：16 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0198-house-robber/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
