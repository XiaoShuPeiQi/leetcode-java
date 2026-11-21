# Hot100 第 67 题 · 力扣 153 · 寻找旋转排序数组中的最小值 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0153-find-minimum-in-rotated-sorted-array/

Hot100 · 二分查找 · Find Minimum In Rotated Sorted Array

## 题意

找出由无重复的升序数组旋转得到的数组中的最小值。要求 O(log n) 时间。

官方原题：https://leetcode.cn/problems/find-minimum-in-rotated-sorted-array/

## 课堂示例

输入：nums = [4, 5, 6, 7, 0, 1, 2]

预期结果：0

实际结果：0

测试来源：飞书 Hot100 第 67 题「实例走过程」

## 解法

每轮用中点与当前右端点的大小关系，判断最小值还可能在哪一半。

## 状态含义

右侧有跳变时做 left = mid + 1，右侧递增时做 right = mid，保留中点本身。搜索收敛到一个下标后，返回 nums[left]。

## 状态变化

每轮用中点与当前右端点的大小关系，判断最小值还可能在哪一半。

## Java 解法

```java
class Solution {
    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] > nums[right]) {
                // mid在前半段（较大元素），最小值在右边
                left = mid + 1;
            } else {
                // mid在后半段（较小元素），最小值在左边或就是mid
                right = mid;
            }
        }

        return nums[left];
    }
}
```

## 来源与版本

代码随想录 · Java。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/array/LC0153FindMinimumInRotatedSortedArray.java

源码指纹：ab92c26f10716a5128bb0cdff290597b169cfb8ea76541564167de66026e04d9

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcnnuHLcx3Wh83zqztuqAtvZc（外部文档可能需要登录，本文仅包含本页已发布的内容。）

代码随想录 Java 解法：https://programmercarl.com/hot100/0153.find-minimum-in-rotated-sorted-array.html

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：21 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0153-find-minimum-in-rotated-sorted-array/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
