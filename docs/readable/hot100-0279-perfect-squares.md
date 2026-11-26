# Hot100 第 84 题 · 力扣 279 · 完全平方数 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0279-perfect-squares/

Hot100 · 动态规划 · Perfect Squares

## 题意

给你一个整数 `n` ，返回 *和为 `n` 的完全平方数的最少数量* 。

**完全平方数** 是一个整数，其值等于另一个整数的平方；换句话说，其值等于一个整数自乘的积。例如，`1`、`4`、`9` 和 `16` 都是完全平方数，而 `3` 和 `11` 不是。

**示例 1：**

```text
输入：n = 12
输出：3
解释：12 = 4 + 4 + 4
```

**示例 2：**

```text
输入：n = 13
输出：2
解释：13 = 4 + 9
```

**提示：**

- `1 <= n <= 10^4`

官方原题：https://leetcode.cn/problems/perfect-squares

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0200-0299/0279.Perfect%20Squares/README.md

## 回放用例

输入：n = 12

预期结果：3

实际结果：3

测试来源：算法学习总笔记的原推演，与本地 Java 主程序一致。

## 解法

动态规划

## 状态含义

dp[i] 表示总和恰为 i 时最少需要几个完全平方数。

## 状态变化

把平方数当作可重复使用的硬币，求最少数量。

## Java 解法

```java
class Solution {
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            dp[i] = i;
            for (int j = 1; j * j <= i; j++) {
                dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
            }
        }
        return dp[n];
    }
}
```

## 来源与版本

誊写算法学习总笔记的主解和原示例。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/dp/LC0279PerfectSquares.java

源码指纹：89af0f156120c9a32a9241c3c804556289e3d184975b518265add80ea15dac4b

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcnCnc9Xgm1eve2M9Am8jvAFe（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：91 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0279-perfect-squares/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
