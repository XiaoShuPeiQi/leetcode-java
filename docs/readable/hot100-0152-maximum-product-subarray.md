# Hot100 第 88 题 · 力扣 152 · 乘积最大子数组 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0152-maximum-product-subarray/

Hot100 · 动态规划 · Maximum Product Subarray

## 题意

给你一个整数数组 `nums` ，请你找出数组中乘积最大的非空连续 子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。

测试用例的答案是一个 **32-位** 整数。

**请注意**，一个只包含一个元素的数组的乘积是这个元素的值。

**示例 1:**

```text
输入: nums = [2,3,-2,4]
输出: `6`
解释: 子数组 [2,3] 有最大乘积 6。
```

**示例 2:**

```text
输入: nums = [-2,0,-1]
输出: 0
解释: 结果不能为 2, 因为 [-2,-1] 不是子数组。
```

**提示:**

- `1 <= nums.length <= 2 * 104`
- `-10 <= nums[i] <= 10`
- `nums` 的任何子数组的乘积都 **保证** 是一个 **32-位** 整数

官方原题：https://leetcode.cn/problems/maximum-product-subarray/

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0100-0199/0152.Maximum%20Product%20Subarray/README.md

## 回放用例

输入：nums = [-2, 3, -4]

预期结果：24

实际结果：24

测试来源：本节推演选用的小用例。

## 解法

同时维护最大和最小结尾乘积

## 状态含义

maxEnding 和 minEnding 分别表示以当前位置结尾的最大、最小乘积。每次在当前数、旧最大值乘当前数、旧最小值乘当前数中取最大和最小。result 记录所有结尾里的最大值。

## 状态变化

同一位置同时记最大和最小结尾乘积，遇到负数时，过去最小的可能变成最大的。

## Java 解法

```java
class Solution {
    public int maxProduct(int[] nums) {
        int maxEnding = nums[0];
        int minEnding = nums[0];
        int result = nums[0];

        for (int i = 1; i < nums.length; i++) {
            int previousMax = maxEnding;
            maxEnding = Math.max(nums[i], Math.max(previousMax * nums[i], minEnding * nums[i]));
            minEnding = Math.min(nums[i], Math.min(previousMax * nums[i], minEnding * nums[i]));
            result = Math.max(result, maxEnding);
        }
        return result;
    }
}
```

## 来源与版本

依据代码随想录的解法思路整理 Java 版。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/dp/LC0152MaximumProductSubarray.java

源码指纹：9c6acef82753eea3fb6a22010622c7da5a50bf309cf26f86a438f7c5dfb2f98b

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcnxROAFkLsZ3HwWPcGr6iBvh（外部文档可能需要登录，本文仅包含本页已发布的内容。）

代码随想录 Java 解法：https://programmercarl.com/hot100/0152.maximum-product-subarray.html

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：17 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0152-maximum-product-subarray/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
