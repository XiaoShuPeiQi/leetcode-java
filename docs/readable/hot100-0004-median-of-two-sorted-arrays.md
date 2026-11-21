# Hot100 第 68 题 · 力扣 4 · 寻找两个正序数组的中位数 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0004-median-of-two-sorted-arrays/

Hot100 · 二分查找 · Median Of Two Sorted Arrays

## 题意

给定两个各自按非递减顺序排列的数组，求合并后有序序列的中位数。时间复杂度要求为 O(log(m+n))。

官方原题：https://leetcode.cn/problems/median-of-two-sorted-arrays/

## 课堂示例

输入：nums1 = [1, 3], nums2 = [2, 4]

预期结果：2.5

实际结果：2.5

测试来源：飞书 Hot100 第 68 题「实例走过程」

## 解法

把中位数转成第 k 小问题，每次排除一段确认排在第 k 小之前的元素，同时减少 k。

## 状态含义

跳过 half 个元素时，只移动对应的一侧起点，并把名次改成 k - half。某侧凑不够 half 个，用本题值域外的 Integer.MAX_VALUE 作探针，让足够长的一侧先排除。若一侧耗尽，就在另一侧直接取第 k 个。若 k == 1，取两个后缀首元素中的较小值。

## 状态变化

把中位数转成第 k 小问题，每次排除一段确认排在第 k 小之前的元素，同时减少 k。

## Java 解法

```java
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int total = m + n;

        if (total % 2 == 1) {
            return findKth(nums1, 0, nums2, 0, total / 2 + 1);
        } else {
            int left = findKth(nums1, 0, nums2, 0, total / 2);
            int right = findKth(nums1, 0, nums2, 0, total / 2 + 1);
            return (left + right) / 2.0;
        }
    }

    private int findKth(int[] nums1, int start1, int[] nums2, int start2, int k) {
        if (start1 >= nums1.length) {
            return nums2[start2 + k - 1];
        }
        if (start2 >= nums2.length) {
            return nums1[start1 + k - 1];
        }
        if (k == 1) {
            return Math.min(nums1[start1], nums2[start2]);
        }

        int half = k / 2;
        int mid1 = start1 + half - 1 < nums1.length ? nums1[start1 + half - 1] : Integer.MAX_VALUE;
        int mid2 = start2 + half - 1 < nums2.length ? nums2[start2 + half - 1] : Integer.MAX_VALUE;

        if (mid1 < mid2) {
            return findKth(nums1, start1 + half, nums2, start2, k - half);
        } else {
            return findKth(nums1, start1, nums2, start2 + half, k - half);
        }
    }
}
```

## 来源与版本

代码随想录 · Java。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/array/LC0004MedianOfTwoSortedArrays.java

源码指纹：3c9f234d7c237f221a8efb7a657ba3d19b14427181d8986d71eea5a6373efbeb

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcn4IVsHaiIyYYws7EuWtmxLf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

代码随想录 Java 解法：https://programmercarl.com/hot100/0004.median-of-two-sorted-arrays.html

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：50 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0004-median-of-two-sorted-arrays/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
