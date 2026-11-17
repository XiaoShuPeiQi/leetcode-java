# 494 · 目标和 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0494-target-sum/

动态规划 · Target Sum

## 题意

为 nums 中每个元素选择正号或负号，求表达式结果等于 target 的符号分配数。

官方原题：https://leetcode.cn/problems/target-sum/

## 课堂示例

输入：nums = [1, 1, 1, 1, 1], target = 3

预期结果：5

实际结果：5

测试来源：本章实例走过程；与本地main和网页回放输入一致。采用力扣公开示例。

## 状态含义

dp[j] 表示从已处理下标中选出总和 j 的方案数；相同数值的不同下标仍是不同选择。

## 状态变化

dp[j] += dp[j-num]：保留不选当前下标的方案，再加选它的方案。

## Java 解法

```java
import java.util.*;

class Solution {
    public int findTargetSumWays(int[] nums, int target) {
        int sum = 0;
        for (int num : nums) sum += num;
        if (Math.abs(target) > sum || (sum + target) % 2 != 0) return 0;
        int bag = (sum + target) / 2;
        int[] dp = new int[bag + 1];
        dp[0] = 1;
        for (int num : nums) {
            for (int j = bag; j >= num; j--) {
                dp[j] += dp[j - num]; // num=0 时也要更新，正负零是两种方案
            }
        }
        return dp[bag];
    }
}
```

## 来源与版本

算法与本章Java代码一致，仅增加包、独立main、static及运行适配；同题旧解法保留。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/dp/LC0494TargetSum.java

源码指纹：d0270a73a8a725b070d667ce84cb3afcde3ea92ac38a785166d216b72f8a8cc8

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：60 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0494-target-sum/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
