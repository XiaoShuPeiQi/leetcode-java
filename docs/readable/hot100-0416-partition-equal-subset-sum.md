# Hot100 第 89 题 · 力扣 416 · 分割等和子集 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0416-partition-equal-subset-sum/

Hot100 · 动态规划 · Partition Equal Subset Sum

## 题意

给你一个 **只包含正整数** 的 **非空** 数组 `nums` 。请你判断是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。

**示例 1：**

```text
输入：nums = [1,5,11,5]
输出：true
解释：数组可以分割成 [1, 5, 5] 和 [11] 。
```

**示例 2：**

```text
输入：nums = [1,2,3,5]
输出：false
解释：数组不能分割成两个元素和相等的子集。
```

**提示：**

- `1 <= nums.length <= 200`

- `1 <= nums[i] <= 100`

官方原题：https://leetcode.cn/problems/partition-equal-subset-sum

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0400-0499/0416.Partition%20Equal%20Subset%20Sum/README.md

## 回放用例

输入：nums = [1, 5, 11, 5]

预期结果：true

实际结果：true

测试来源：算法学习总笔记的原推演，与本地 Java 主程序一致。

## 解法

动态规划

## 状态含义

dp[j] 表示已处理的数能否组成恰好为 j 的子集。

## 状态变化

每个数只能用一次，检查能否恰好凑出总和的一半。

## Java 解法

```java
class Solution {
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int num : nums) sum += num;
        if (sum % 2 != 0) return false;
        int target = sum / 2;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;
        for (int num : nums) {
            for (int j = target; j >= num; j--) {
                dp[j] = dp[j] || dp[j - num]; // 倒序：当前数最多选一次
            }
        }
        return dp[target];
    }
}
```

## 来源与版本

誊写算法学习总笔记的主解和原示例。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/dp/LC0416PartitionEqualSubsetSum.java

源码指纹：eb299be0d0bbea08ae59f166e91cec110523e6757cbc77f548e7d96d67ae690d

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcnRL0BrtRxzqHiRGs2eiEief（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：70 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0416-partition-equal-subset-sum/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
