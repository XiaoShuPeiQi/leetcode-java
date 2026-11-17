# 377 · 组合总和 Ⅳ — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0377-combination-sum-iv/

动态规划 · Combination Sum IV

## 题意

用 nums 中互不相同的正整数凑出 target，每个数可以重复使用。不同的排列顺序也分别计数，求方案数。

官方原题：https://leetcode.cn/problems/combination-sum-iv/

## 课堂示例

输入：nums = [1, 2, 3], target = 4

预期结果：7

实际结果：7

测试来源：本章实例走过程；与本地main和网页回放输入一致。采用力扣公开示例。

## 状态含义

dp[i] 表示使用 nums 中的正整数，按顺序组成总和 i 的序列数。

## 状态变化

对每个 num<=i，累加 dp[i-num]。

## Java 解法

```java
import java.util.*;

class Solution {
    public int combinationSum4(int[] nums, int target) {
        long[] dp = new long[target + 1];
        dp[0] = 1;
        for (int i = 1; i <= target; i++) {
            for (int num : nums) {
                if (num <= i) {
                    dp[i] = Math.min(Integer.MAX_VALUE, dp[i] + dp[i - num]);
                }
            }
        }
        return (int) dp[target];
    }
}
```

## 来源与版本

算法与本章Java代码一致，仅增加包、独立main、static及运行适配；同题旧解法保留。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/dp/LC0377CombinationSumIV.java

源码指纹：1abc01f25461b9ec8ef9c6f26262f78c995c7a38aa210260117457e0170d11c8

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：47 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0377-combination-sum-iv/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
