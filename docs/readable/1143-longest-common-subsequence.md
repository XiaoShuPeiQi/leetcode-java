# 1143 · 最长公共子序列 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/1143-longest-common-subsequence/

动态规划 · Longest Common Subsequence

## 题意

在两个字符串中分别删除若干字符，使剩下的两个序列相同且保持原相对顺序。求最长公共子序列的长度。

官方原题：https://leetcode.cn/problems/longest-common-subsequence/

## 课堂示例

输入：text1 = "abcde", text2 = "ace"

预期结果：3

实际结果：3

测试来源：本章实例走过程；与本地main和网页回放输入一致。采用力扣公开示例。

## 状态含义

dp[i][j] 表示 text1前i个字符与text2前j个字符的最长公共子序列长度。

## 状态变化

相等：dp[i-1][j-1]+1；不相等：max(dp[i-1][j],dp[i][j-1])。

## Java 解法

```java
import java.util.*;

class Solution {
    public int longestCommonSubsequence(String text1, String text2) {
        int[][] dp = new int[text1.length() + 1][text2.length() + 1];
        for (int i = 1; i <= text1.length(); i++) {
            for (int j = 1; j <= text2.length(); j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[text1.length()][text2.length()];
    }
}
```

## 来源与版本

算法与本章Java代码一致，仅增加包、独立main、static及运行适配；同题旧解法保留。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/dp/LC1143LongestCommonSubsequence.java

源码指纹：61fb46e4b4fee3751949dad9db17aa6cfe05c3194d08beaad5bcb5bd0d4a16ba

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：60 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/1143-longest-common-subsequence/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
