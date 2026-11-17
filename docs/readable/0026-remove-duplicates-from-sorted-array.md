# 26 · 删除有序数组中的重复项 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0026-remove-duplicates-from-sorted-array/

数组 · Remove Duplicates From Sorted Array

## 题意

在非递减数组 nums 中原地压缩重复值，使每个值只出现一次，原有顺序不变。返回保留个数 k，判题读取前 k 项。

官方原题：https://leetcode.cn/problems/remove-duplicates-from-sorted-array/

## 课堂示例

输入：nums = [0, 0, 1, 1, 2]

预期结果：[0, 1, 2]

实际结果：[0, 1, 2]

测试来源：本次数组章节实例走过程；题目链接 https://leetcode.cn/problems/remove-duplicates-from-sorted-array/

## 状态含义

slow同时表示有效长度和下次写入位置；nums[slow-1]是上一个已保留的值。

## 状态变化

有序让重复值相邻，只把与有效前缀末尾不同的数写进去。

## Java 解法

```java
import java.util.*;

class Solution {
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int slow = 1; // 非空时，第一个数一定保留
        for (int fast = 1; fast < nums.length; fast++) {
            if (nums[fast] != nums[slow - 1]) {
                nums[slow] = nums[fast];
                slow++;
            }
        }
        return slow;
    }
}
```

## 来源与版本

Solution类体与数组笔记主解一致，只增加static、外层类和独立main。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/array/LC0026RemoveDuplicatesFromSortedArray.java

源码指纹：e90326c60bfa43b9a44f76eee50891361da9ec30c1f605a437da7c8c8019393b

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/SYdww8LbniCUBckZHRDcZ8QSnWb（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：18 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0026-remove-duplicates-from-sorted-array/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
