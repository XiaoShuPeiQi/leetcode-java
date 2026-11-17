# 647 · 回文子串 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0647-palindromic-substrings/

动态规划 · Palindromic Substrings

## 题意

统计 s 中所有非空回文子串的个数。内容相同但下标区间不同的子串分别计数。

官方原题：https://leetcode.cn/problems/palindromic-substrings/

## 课堂示例

输入：s = "aaa"

预期结果：6

实际结果：6

测试来源：本章实例走过程；与本地main和网页回放输入一致。采用力扣公开示例。

## 状态含义

dp[i][j] 表示闭区间 s[i..j] 是否为回文子串；result统计合法区间数。

## 状态变化

s[i]==s[j] 且（j-i<=1 或 dp[i+1][j-1]）时为true，result加1。

## Java 解法

```java
import java.util.*;

class Solution {
    public int countSubstrings(String s) {
        boolean[][] dp = new boolean[s.length()][s.length()];
        int result = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            for (int j = i; j < s.length(); j++) {
                if (s.charAt(i) == s.charAt(j) && (j - i <= 1 || dp[i + 1][j - 1])) {
                    dp[i][j] = true;
                    result++;
                }
            }
        }
        return result;
    }
}
```

## 来源与版本

算法与本章Java代码一致，仅增加包、独立main、static及运行适配；同题旧解法保留。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/dp/LC0647PalindromicSubstrings.java

源码指纹：9cd977b31bca6acf815fc92d9a1f01b213a6c1c52979237f42ee97f10cd665d4

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：36 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0647-palindromic-substrings/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
