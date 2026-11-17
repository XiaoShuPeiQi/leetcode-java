# 416 · 分割等和子集 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0416-partition-equal-subset-sum/

动态规划 · Partition Equal Subset Sum

## 题意

判断能否把正整数数组分成两个子集，使两个子集的元素和相等，每个位置恰好分到一边。

官方原题：https://leetcode.cn/problems/partition-equal-subset-sum/

## 课堂示例

输入：nums = [1, 5, 11, 5]

预期结果：true

实际结果：true

测试来源：本章实例走过程；与本地main和网页回放输入一致。采用力扣公开示例。

## 状态含义

dp[j] 表示已处理的数能否组成恰好为 j 的子集。

## 状态变化

不选当前 num 时保留旧 dp[j]；选它时看旧 dp[j-num]，所以 dp[j] |= dp[j-num]。

## Java 解法

```java
import java.util.*;

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

算法与本章Java代码一致，仅增加包、独立main、static及运行适配；同题旧解法保留。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/dp/LC0416PartitionEqualSubsetSum.java

源码指纹：eb299be0d0bbea08ae59f166e91cec110523e6757cbc77f548e7d96d67ae690d

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：70 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0416-partition-equal-subset-sum/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
