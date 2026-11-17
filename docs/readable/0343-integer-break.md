# 343 · 整数拆分 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0343-integer-break/

动态规划 · Integer Break

## 题意

把 n 拆成至少两个正整数的和，求这些整数的乘积最大可以是多少。

官方原题：https://leetcode.cn/problems/integer-break/

## 课堂示例

输入：n = 5

预期结果：6

实际结果：6

测试来源：本章实例走过程；与本地main和网页回放输入一致。自选小规模示例。

## 状态含义

dp[i] 表示整数 i 至少拆成两个正整数后的最大乘积。

## 状态变化

对每个1<=j<i，候选是 j*(i-j) 与 j*dp[i-j]，与当前 dp[i] 取最大。

## Java 解法

```java
import java.util.*;

class Solution {
    public int integerBreak(int n) {
        int[] dp = new int[n + 1];
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j < i; j++) {
                int product = Math.max(j * (i - j), j * dp[i - j]);
                dp[i] = Math.max(dp[i], product);
            }
        }
        return dp[n];
    }
}
```

## 来源与版本

算法与本章Java代码一致，仅增加包、独立main、static及运行适配；同题旧解法保留。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/dp/LC0343IntegerBreak.java

源码指纹：628d93f035391869f701d2df666664a77c748974175e7fc4c05b0709b9a1c0cb

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：43 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0343-integer-break/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
