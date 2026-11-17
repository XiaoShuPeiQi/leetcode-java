# 474 · 一和零 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0474-ones-and-zeroes/

动态规划 · Ones and Zeroes

## 题意

从由 0 和 1 构成的字符串数组中选择尽量多的字符串，使 0 的总数不超过 m，1 的总数不超过 n。返回能选的最大数量。

官方原题：https://leetcode.cn/problems/ones-and-zeroes/

## 课堂示例

输入：strs = ["10", "0001", "111001", "1", "0"], m = 5, n = 3

预期结果：4

实际结果：4

测试来源：本章实例走过程；与本地main和网页回放输入一致。采用力扣公开示例。

## 状态含义

dp[i][j] 表示最多使用 i 个0和 j 个1时，可以选择的最多字符串数。

## 状态变化

当前串有 zeros 个0、ones 个1：dp[i][j]=max(dp[i][j],dp[i-zeros][j-ones]+1)。

## Java 解法

```java
import java.util.*;

class Solution {
    public int findMaxForm(String[] strs, int m, int n) {
        int[][] dp = new int[m + 1][n + 1];
        for (String str : strs) {
            int zeros = 0, ones = 0;
            for (char c : str.toCharArray()) {
                if (c == '0') zeros++;
                else ones++;
            }
            for (int i = m; i >= zeros; i--) {
                for (int j = n; j >= ones; j--) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - zeros][j - ones] + 1);
                }
            }
        }
        return dp[m][n];
    }
}
```

## 来源与版本

算法与本章Java代码一致，仅增加包、独立main、static及运行适配；同题旧解法保留。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/dp/LC0474OnesandZeroes.java

源码指纹：7abf75597bb75dc783f19bc4a03613122baa5ab28b5bb4ea78c5398690b0af1b

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：230 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0474-ones-and-zeroes/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
