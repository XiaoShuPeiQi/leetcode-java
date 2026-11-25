# Hot100 第 65 题 · 力扣 34 · 在排序数组中查找元素的第一个和最后一个位置 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0034-find-first-and-last-position-of-element-in-sorted-array/

Hot100 · 二分查找 · Find First And Last Position Of Element In Sorted Array

## 题意

给你一个按照非递减顺序排列的整数数组 `nums`，和一个目标值 `target`。请你找出给定目标值在数组中的开始位置和结束位置。

如果数组中不存在目标值 `target`，返回 `[-1, -1]`。

你必须设计并实现时间复杂度为 `O(log n)` 的算法解决此问题。

**示例 1：**

```text
输入：nums = [5,7,7,8,8,10], target = 8
输出：[3,4]
```

**示例 2：**

```text
输入：nums = [5,7,7,8,8,10], target = 6
输出：[-1,-1]
```

**示例 3：**

```text
输入：nums = [], target = 0
输出：[-1,-1]
```

**提示：**

- `0 <= nums.length <= 10^5`

- `-10^9 <= nums[i] <= 10^9`

- `nums` 是一个非递减数组

- `-10^9 <= target <= 10^9`

官方原题：https://leetcode.cn/problems/find-first-and-last-position-of-element-in-sorted-array

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0000-0099/0034.Find%20First%20and%20Last%20Position%20of%20Element%20in%20Sorted%20Array/README.md

## 回放用例

输入：nums = [5, 7, 7, 8, 8, 10], target = 8

预期结果：[3, 4]

实际结果：[3, 4]

测试来源：飞书 Hot100 第 65 题「实例走过程」

## 状态含义

answer 始终是目前找到的最靠目标侧的命中下标，初始为 −1。找到更靠边的下标就覆盖它。主方法把两次返回值分别存在 first、last，再返回数组 [first,last]。

## 状态变化

二分两次，每次命中先记下位置，再朝要求的那侧找有没有更靠边的目标值。

## Java 解法

```java
class Solution {
    public int[] searchRange(int[] nums, int target) {
        int first = findBoundary(nums, target, true);
        int last = findBoundary(nums, target, false);
        return new int[]{first, last};
    }

    private int findBoundary(int[] nums, int target, boolean findLeft) {
        int left = 0;
        int right = nums.length - 1;
        int answer = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                answer = mid; // 先保存命中，再继续找边界
                if (findLeft) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
        }
        return answer;
    }
}
```

## 来源与版本

算法学习总笔记（Java）原码。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/array/LC0034FindFirstAndLastPositionOfElementInSortedArray.java

源码指纹：8a7a5192d9f198842f7bdaadd329033306d81747d0710fe55d66cab0dbd6d0c8

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcnfwiA9ZyRvESt7zyqGNFtPf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：59 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0034-find-first-and-last-position-of-element-in-sorted-array/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
