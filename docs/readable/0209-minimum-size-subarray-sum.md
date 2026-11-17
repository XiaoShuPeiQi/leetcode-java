# 209 · 长度最小的子数组 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0209-minimum-size-subarray-sum/

数组 · Minimum Size Subarray Sum

## 题意

给定正整数数组和正整数 target，找出和至少为 target 的最短非空连续子数组。找不到则返回 0。

官方原题：https://leetcode.cn/problems/minimum-size-subarray-sum/

## 课堂示例

输入：target = 7, nums = [2, 3, 1, 2, 4, 3]

预期结果：2

实际结果：2

测试来源：本次数组章节实例走过程；题目链接 https://leetcode.cn/problems/minimum-size-subarray-sum/

## 状态含义

sum始终是闭区间[left,right]的和；result是已见过的最短有效长度。

## 状态变化

正数让窗口和随扩张增大；达标后连续收缩，寻找更短答案。

## Java 解法

```java
import java.util.*;

class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int left = 0;
        int result = Integer.MAX_VALUE;
        long sum = 0;
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];
            while (sum >= target) { // 达标后，先记答案再尝试缩短
                result = Math.min(result, right - left + 1);
                sum -= nums[left];
                left++;
            }
        }
        return result == Integer.MAX_VALUE ? 0 : result;
    }
}
```

## 来源与版本

Solution类体与数组笔记主解一致，只增加static、外层类和独立main。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/array/LC0209MinimumSizeSubarraySum.java

源码指纹：f8258b5850cc52b974f9c3aa93c30c153f35afaaa6bb14c285925c68b10252af

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/SYdww8LbniCUBckZHRDcZ8QSnWb（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：45 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0209-minimum-size-subarray-sum/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
