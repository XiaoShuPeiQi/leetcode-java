# 115 · 不同的子序列 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0115-distinct-subsequences/

动态规划 · Distinct Subsequences

## 题意

从 s 删除若干位置的字符，使剩余字符按原顺序组成 t。求不同的下标选择方案数。

官方原题：https://leetcode.cn/problems/distinct-subsequences/

## 课堂示例

输入：s = "rabbbit", t = "rabbit"

预期结果：3

实际结果：3

测试来源：本章实例走过程；与本地main和网页回放输入一致。采用力扣公开示例。

## 状态含义

dp[i][j] 表示 s前i个字符中，选出等于t前j个字符的子序列的方案数；按下标选择计数。

## 状态变化

相等：dp[i-1][j-1]+dp[i-1][j]；不等：dp[i-1][j]。

## Java 解法

```java
import java.util.*;

class Solution {
    public int numDistinct(String s, String t) {
        long[][] dp = new long[s.length() + 1][t.length() + 1];
        for (int i = 0; i <= s.length(); i++) dp[i][0] = 1;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 1; j <= t.length(); j++) {
                dp[i][j] = dp[i - 1][j]; // 不使用当前 s 字符
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[i][j] = Math.min(Integer.MAX_VALUE, dp[i][j] + dp[i - 1][j - 1]);
                }
            }
        }
        return (int) dp[s.length()][t.length()];
    }
}
```

## 来源与版本

算法与本章Java代码一致，仅增加包、独立main、static及运行适配；同题旧解法保留。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/dp/LC0115DistinctSubsequences.java

源码指纹：9d0bf2d1e02d53673c89c2583f47d37ab19b0221b3e01b1dbcc3419c79d75308

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：156 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0115-distinct-subsequences/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
