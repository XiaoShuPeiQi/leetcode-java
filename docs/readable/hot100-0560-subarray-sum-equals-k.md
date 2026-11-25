# 560 · 和为 K 的子数组 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0560-subarray-sum-equals-k/

Hot100 · 子串 · Subarray Sum Equals K

## 题意

给你一个整数数组 `nums` 和一个整数 `k` ，请你统计并返回 *该数组中和为 `k` 的子数组的个数* 。

子数组是数组中元素的连续非空序列。

**示例 1：**

```text
输入：nums = [1,1,1], k = 2
输出：2
```

**示例 2：**

```text
输入：nums = [1,2,3], k = 3
输出：2
```

**提示：**

- `1 <= nums.length <= 2 * 10^4`

- `-1000 <= nums[i] <= 1000`

- `-10^7 <= k <= 10^7`

官方原题：https://leetcode.cn/problems/subarray-sum-equals-k

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0500-0599/0560.Subarray%20Sum%20Equals%20K/README.md

## 回放用例

输入：nums=[1,1,1]; k=2

预期结果：2

实际结果：2

测试来源：力扣公开示例（已读取题目与Java模板）

## 状态含义

sum是当前前缀和；count[prefix]是此前该前缀和出现次数；res统计已找到的非空子数组。

## 状态变化

当前前缀和为sum时，统计之前出现了多少次sum-k。

## Java 解法

```java
import java.util.*;

class Solution {
    public int subarraySum(int[] nums, int k) {
        int res = 0; // 保存和为k的非空子数组个数
        Map<Integer, Integer> count = new HashMap<>();
        count.put(0, 1); // 空前缀出现一次，使从下标0开始的区间也能被统计
        int sum = 0;

        for (int num : nums) {
            sum += num;
            res += count.getOrDefault(sum - k, 0); // 每一个匹配的旧前缀，都对应一个合法区间

            // 先统计答案，再记录当前前缀，避免k=0时把空区间计入
            count.put(sum, count.getOrDefault(sum, 0) + 1);
        }
        return res;
    }
}
```

## 来源与版本

学习扩展：总笔记未检出本题完整题解，按已有章节的方法组织新解法。 真实JDI回放对应的Solution类体，算法中未加入调试输出。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/hashtable/LC0560SubarraySumEqualsK.java

源码指纹：c09e98d795e14dc03041751eea60661cac32fb0918c92df032c741da8ecba0ff

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：20 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0560-subarray-sum-equals-k/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
