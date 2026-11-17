# 1035 · 不相交的线 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/1035-uncrossed-lines/

动态规划 · Uncrossed Lines

## 题意

给两排数字连线，只能连接值相等的两个位置，每个位置最多用一次，连线互不相交。返回最多可以画的线数。

官方原题：https://leetcode.cn/problems/uncrossed-lines/

## 课堂示例

输入：nums1 = [1, 4, 2], nums2 = [1, 2, 4]

预期结果：2

实际结果：2

测试来源：本章实例走过程；与本地main和网页回放输入一致。采用力扣公开示例。

## 状态含义

dp[i][j] 表示两个数组前 i、j 项之间最多能画多少条不相交线。

## 状态变化

数字相等时接左上角+1；否则尝试跳过任一边末尾，取上/左最大值。

## Java 解法

```java
import java.util.*;

class Solution {
    public int maxUncrossedLines(int[] nums1, int[] nums2) {
        int[][] dp = new int[nums1.length + 1][nums2.length + 1];
        for (int i = 1; i <= nums1.length; i++) {
            for (int j = 1; j <= nums2.length; j++) {
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[nums1.length][nums2.length];
    }
}
```

## 来源与版本

算法与本章Java代码一致，仅增加包、独立main、static及运行适配；同题旧解法保留。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/dp/LC1035UncrossedLines.java

源码指纹：89db29c3aec5a4d7dca9ddd0d85abd6b0cee5edd96f4389eac3140b43306afff

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：38 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/1035-uncrossed-lines/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
