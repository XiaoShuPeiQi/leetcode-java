# 1049 · 最后一块石头的重量 II — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/1049-last-stone-weight-ii/

动态规划 · Last Stone Weight II

## 题意

每次任选两块石头相撞，等重则都消失，不等重则留下重量之差。最终至多剩一块，求可实现的最小剩余重量。

官方原题：https://leetcode.cn/problems/last-stone-weight-ii/

## 课堂示例

输入：stones = [2, 7, 4, 1, 8, 1]

预期结果：1

实际结果：1

测试来源：本章实例走过程；与本地main和网页回放输入一致。采用力扣公开示例。

## 状态含义

dp[j] 表示背包容量为 j 时，已处理石头能放入的最大总重量。

## 状态变化

dp[j]=max(dp[j],dp[j-stone]+stone)。只背起总和一半以内最重的一组。

## Java 解法

```java
import java.util.*;

class Solution {
    public int lastStoneWeightII(int[] stones) {
        int sum = 0;
        for (int stone : stones) sum += stone;
        int target = sum / 2;
        int[] dp = new int[target + 1];
        for (int stone : stones) {
            for (int j = target; j >= stone; j--) {
                dp[j] = Math.max(dp[j], dp[j - stone] + stone);
            }
        }
        return sum - 2 * dp[target];
    }
}
```

## 来源与版本

算法与本章Java代码一致，仅增加包、独立main、static及运行适配；同题旧解法保留。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/dp/LC1049LastStoneWeightII.java

源码指纹：5e76995264ab348c547e8fa04886b32d1cc1e4d50f284270048e0e35f0a9411c

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：118 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/1049-last-stone-weight-ii/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
