# 53 · 最大子数组和 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/dp-0053-maximum-subarray/

动态规划 · Maximum Subarray

## 题意

在非空整数数组中选一段非空连续子数组，返回它能取得的最大和。

官方原题：https://leetcode.cn/problems/maximum-subarray/

## 课堂示例

输入：nums = [-2, 1, -3, 4, -1, 2, 1]

预期结果：6

实际结果：6

测试来源：本章实例走过程；与本地main和网页回放输入一致。自选小规模示例。

## 状态含义

dp[i] 表示必须以 nums[i] 结束的非空连续子数组的最大和。result 是所有结尾中的最大值。

## 状态变化

dp[i]=max(nums[i],dp[i-1]+nums[i])；result=max(result,dp[i])。

## Java 解法

```java
import java.util.*;

class Solution {
    public int maxSubArray(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int result = dp[0];
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(nums[i], dp[i - 1] + nums[i]);
            result = Math.max(result, dp[i]);
        }
        return result;
    }
}
```

## 来源与版本

算法与本章Java代码一致，仅增加包、独立main、static及运行适配；同题旧解法保留。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/greedy/LC0053MaximumSubarray.java

源码指纹：a26cb5440f5909535b79c8d925836ff64ed98c6add94cbed8891137440b5365b

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：25 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/dp-0053-maximum-subarray/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
