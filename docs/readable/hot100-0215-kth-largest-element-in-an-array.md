# Hot100 第 74 题 · 力扣 215 · 数组中的第 K 个最大元素 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0215-kth-largest-element-in-an-array/

Hot100 · 堆 · Kth Largest Element In An Array

## 题意

给定整数数组 `nums` 和整数 `k`，请返回数组中第 `**k**` 个最大的元素。

请注意，你需要找的是数组排序后的第 `k` 个最大的元素，而不是第 `k` 个不同的元素。

你必须设计并实现时间复杂度为 `O(n)` 的算法解决此问题。

**示例 1:**

```text
输入: [3,2,1,5,6,4], k = 2
输出: 5
```

**示例 2:**

```text
输入: [3,2,3,1,2,4,5,5,6], k = 4
输出: 4
```

**提示：**

- `1 <= k <= nums.length <= 10^5`

- `-10^4 <= nums[i] <= 10^4`

官方原题：https://leetcode.cn/problems/kth-largest-element-in-an-array

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0200-0299/0215.Kth%20Largest%20Element%20in%20an%20Array/README.md

## 回放用例

输入：nums = [3, 2, 3, 1, 2, 4, 5, 5, 6], k = 4

预期结果：4

实际结果：4

测试来源：飞书 Hot100 第 74 题「实例走过程」

## 解法

随机三路快速选择

## 状态含义

分区期间，[left, less) 已确认小于基准，[less, scan) 等于基准，[scan, greater] 还没检查，(greater, right] 已确认大于基准。分区结束时等于段为 [less, greater]。

## 状态变化

把第 K 大换成升序下标 n - k，每轮把候选区间按基准值分三段，然后只到目标所在的一段继续找。

## Java 解法

```java
import java.util.concurrent.ThreadLocalRandom;

class Solution {
    public int findKthLargest(int[] nums, int k) {
        int targetIndex = nums.length - k;
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int pivotIndex = ThreadLocalRandom.current().nextInt(left, right + 1);
            int pivot = nums[pivotIndex];
            int less = left;
            int scan = left;
            int greater = right;

            // 把候选区间分成小于、等于、大于 pivot 的三段。
            while (scan <= greater) {
                if (nums[scan] < pivot) {
                    swap(nums, less, scan);
                    less++;
                    scan++;
                } else if (nums[scan] > pivot) {
                    swap(nums, scan, greater);
                    greater--; // 换到 scan 的数还需要检查。
                } else {
                    scan++;
                }
            }

            if (targetIndex < less) {
                right = less - 1;
            } else if (targetIndex > greater) {
                left = greater + 1;
            } else {
                return pivot;
            }
        }

        return nums[left];
    }

    private void swap(int[] nums, int first, int second) {
        int saved = nums[first];
        nums[first] = nums[second];
        nums[second] = saved;
    }
}
```

## 来源与版本

代码随想录 · 快速选择法的 Java 三路分区实现。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/stackqueue/LC0215KthLargestElementInAnArray.java

源码指纹：8236e9bbc6af8e03177876938c8fedfad6636d7c54a2f46ef74dda33a14c79f6

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcnjeIfT1DyWnn52GOBLFMhmc（外部文档可能需要登录，本文仅包含本页已发布的内容。）

代码随想录 Java 解法：https://programmercarl.com/hot100/0215.kth-largest-element-in-an-array.html

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：253 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0215-kth-largest-element-in-an-array/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
