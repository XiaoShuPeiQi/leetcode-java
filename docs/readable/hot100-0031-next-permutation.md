# Hot100 第 99 题 · 力扣 31 · 下一个排列 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0031-next-permutation/

Hot100 · 技巧 · Next Permutation

## 题意

整数数组的一个 **排列**  就是将其所有成员以序列或线性顺序排列。

- 例如， `arr = [1, 2, 3]` ，以下这些都可以视作 `arr` 的排列： `[1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1]` 。

整数数组的 **下一个排列** 是指其整数的下一个字典序更大的排列。更正式地，如果数组的所有排列根据其字典顺序从小到大排列在一个容器中，那么数组的 **下一个排列** 就是在这个有序容器中排在它后面的那个排列。如果不存在下一个更大的排列，那么这个数组必须重排为字典序最小的排列（即，其元素按升序排列）。

- `arr = [1, 2, 3]` 的下一个排列是 `[1, 3, 2]` 。
- `arr = [2, 3, 1]` 的下一个排列是 `[3, 1, 2]` 。
- `arr = [3, 2, 1]` 的下一个排列是 `[1, 2, 3]` ，因为 `[3, 2, 1]` 不存在一个字典序更大的排列。

给你一个整数数组 `nums` ，找出 `nums` 的下一个排列。

必须** 原地 **修改，只允许使用额外常数空间。

**示例 1：**

```text
输入：nums = [1,2,3]
输出：[1,3,2]
```

**示例 2：**

```text
输入：nums = [3,2,1]
输出：[1,2,3]
```

**示例 3：**

```text
输入：nums = [1,1,5]
输出：[1,5,1]
```

**提示：**

- `1 <= nums.length <= 100`
- `0 <= nums[i] <= 100`

官方原题：https://leetcode.cn/problems/next-permutation/

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0000-0099/0031.Next%20Permutation/README.md

## 回放用例

输入：nums = [1, 3, 5, 4, 2]

预期结果：[1, 4, 2, 3, 5]

实际结果：[1, 4, 2, 3, 5]

测试来源：本节推演选用的小用例。

## 解法

找拐点、交换、反转后缀

## 状态含义

pivot 是最右侧满足 nums[pivot] < nums[pivot+1] 的下标。在右边从末尾找第一个严格大于它的数，交换后再反转整个非递增后缀。

## 状态变化

尽量晚地把一个数抬高一点，再把它后面的数排成最小顺序。

## Java 解法

```java
class Solution {
    public void nextPermutation(int[] nums) {
        int pivot = nums.length - 2;
        while (pivot >= 0 && nums[pivot] >= nums[pivot + 1]) {
            pivot--;
        }

        if (pivot >= 0) {
            int next = nums.length - 1;
            while (nums[next] <= nums[pivot]) {
                next--;
            }
            swap(nums, pivot, next);
        }

        for (int left = pivot + 1, right = nums.length - 1; left < right; left++, right--) {
            swap(nums, left, right);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
```

## 来源与版本

依据代码随想录的解法思路整理 Java 版。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/array/LC0031NextPermutation.java

源码指纹：22ff9e90e31ff23cec58a71b0bb1ecd8bba13167af53d99e0429cd5d5359e375

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcn2rkdrF4jzHieWzFJt7nhNd（外部文档可能需要登录，本文仅包含本页已发布的内容。）

代码随想录 Java 解法：https://programmercarl.com/hot100/0031.next-permutation.html

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：30 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0031-next-permutation/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
