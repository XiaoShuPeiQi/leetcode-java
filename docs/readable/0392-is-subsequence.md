# 392 · 判断子序列 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0392-is-subsequence/

动态规划 · Is Subsequence

## 题意

判断能否从 t 中删除若干字符，保留相对顺序后得到 s。

官方原题：https://leetcode.cn/problems/is-subsequence/

## 课堂示例

输入：s = "abc", t = "ahbgdc"

预期结果：true

实际结果：true

测试来源：本章实例走过程；与本地main和网页回放输入一致。采用力扣公开示例。

## 状态含义

dp[i][j] 表示 s前i个字符与t前j个字符中，能够按顺序匹配的最长长度。

## 状态变化

相等取 dp[i-1][j-1]+1；不等只跳过t的末尾，取dp[i][j-1]。最终长度等于s.length才成功。

## Java 解法

```java
import java.util.*;

class Solution {
    public boolean isSubsequence(String s, String t) {
        int[][] dp = new int[s.length() + 1][t.length() + 1];
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 1; j <= t.length(); j++) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = dp[i][j - 1];
                }
            }
        }
        return dp[s.length()][t.length()] == s.length();
    }
}
```

## 来源与版本

算法与本章Java代码一致，仅增加包、独立main、static及运行适配；同题旧解法保留。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/dp/LC0392IsSubsequence.java

源码指纹：b6e84d3debece10d323d22872e4a9f62e7bb94aaf0b2aee3bf1047b1a7ef15e8

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：65 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0392-is-subsequence/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
