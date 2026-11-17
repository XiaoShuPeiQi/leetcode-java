# 977 · 有序数组的平方 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0977-squares-of-a-sorted-array/

数组 · Squares Of A Sorted Array

## 题意

将非递减整数数组的每项取平方，返回按非递减顺序排列的平方值。

官方原题：https://leetcode.cn/problems/squares-of-a-sorted-array/

## 课堂示例

输入：nums = [-4, -1, 0, 3, 10]

预期结果：[0, 1, 9, 16, 100]

实际结果：[0, 1, 9, 16, 100]

测试来源：本次数组章节实例走过程；题目链接 https://leetcode.cn/problems/squares-of-a-sorted-array/

## 状态含义

[left,right]是未处理原区间；(write,n)是已经排好的较大平方值。

## 状态变化

平方后的最大值一定在当前两端，从结果末尾倒着填。

## Java 解法

```java
import java.util.*;

class Solution {
    public int[] sortedSquares(int[] nums) {
        int[] result = new int[nums.length];
        int left = 0;
        int right = nums.length - 1;
        // 两端选最大平方，倒着填结果
        for (int write = nums.length - 1; write >= 0; write--) {
            int leftSquare = nums[left] * nums[left];
            int rightSquare = nums[right] * nums[right];
            if (leftSquare > rightSquare) {
                result[write] = leftSquare;
                left++;
            } else {
                result[write] = rightSquare;
                right--;
            }
        }
        return result;
    }
}
```

## 来源与版本

Solution类体与数组笔记主解一致，只增加static、外层类和独立main。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/array/LC0977SquaresOfASortedArray.java

源码指纹：f0b4c30dcaf0951fabf1d445209726eae7a1198baccacd5d1f4bacfb827c47f5

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/SYdww8LbniCUBckZHRDcZ8QSnWb（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：37 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0977-squares-of-a-sorted-array/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
