# Hot100 第 87 题 · 力扣 300 · 最长递增子序列 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0300-longest-increasing-subsequence/

Hot100 · 动态规划 · Longest Increasing Subsequence

## 题意

给你一个整数数组 `nums` ，找到其中最长严格递增子序列的长度。

**子序列** 是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，`[3,6,2,7]` 是数组 `[0,3,1,6,2,2,7]` 的子序列。

**示例 1：**

```text
输入：nums = [10,9,2,5,3,7,101,18]
输出：4
解释：最长递增子序列是 [2,3,7,101]，因此长度为 4 。
```

**示例 2：**

```text
输入：nums = [0,1,0,3,2,3]
输出：4
```

**示例 3：**

```text
输入：nums = [7,7,7,7,7,7,7]
输出：1
```

**提示：**

- `1 <= nums.length <= 2500`

- `-10^4 <= nums[i] <= 10^4`

**进阶：**

- 你能将算法的时间复杂度降低到 `O(n log(n))` 吗?

官方原题：https://leetcode.cn/problems/longest-increasing-subsequence

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0300-0399/0300.Longest%20Increasing%20Subsequence/README.md

## 回放用例

输入：nums = [10, 9, 2, 5, 3, 7, 101, 18]

预期结果：4

实际结果：4

测试来源：算法学习总笔记的原推演，与本地 Java 主程序一致。

## 解法

动态规划

## 状态含义

dp[i] 表示以 nums[i] 结尾的最长严格递增子序列长度，必须选 nums[i]。

## 状态变化

固定子序列的最后一个位置，再找前面比它小的结尾接上。

## Java 解法

```java
class Solution {
    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        int result = 0;
        for (int i = 0; i < nums.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) dp[i] = Math.max(dp[i], dp[j] + 1);
            }
            result = Math.max(result, dp[i]);
        }
        return result;
    }
}
```

## 来源与版本

誊写算法学习总笔记的主解和原示例。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/dp/LC0300LongestIncreasingSubsequence.java

源码指纹：d68641477d0f85f32833d5d94ceac0ac6ecdf0fcece5f25c10efa3d47ced9366

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcn39jh0VWshDiZgdJzWy8Fkc（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：94 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0300-longest-increasing-subsequence/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
