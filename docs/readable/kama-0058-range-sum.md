# K58 · 卡码网 58. 区间和 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/kama-0058-range-sum/

数组 · Range Sum

## 题意

题目描述

给定一个整数数组 Array，请计算该数组在每个指定区间内元素的总和。

输入描述

第一行输入为整数数组 Array 的长度 n，接下来 n 行，每行一个整数，表示数组的元素。随后的输入为需要计算总和的区间，直至文件结束。

输出描述

输出每个指定区间内元素的总和。

输入示例

```text
5
1
2
3
4
5
0 1
1 3
```

输出示例

```text
3
9
```

数据范围：

0 < n <= 100000

官方原题：https://kamacoder.com/problempage.php?pid=1070

GitHub 题面来源：https://github.com/youngyangyang04/leetcode-master/blob/6fe1571732d41e8f425f8e702992adf7ffae95a0/problems/kamacoder/0058.%E5%8C%BA%E9%97%B4%E5%92%8C.md

## 回放用例

输入：nums = [1, 2, 3, 4, 5], queries = [[0, 1], [1, 3]]

预期结果：[3, 9]

实际结果：[3, 9]

测试来源：本次数组章节实例走过程；题目链接 https://kamacoder.com/problempage.php?pid=1070

## 状态含义

prefix[i]覆盖[0,i)，不包含下标i；程序按题意持续读取查询直到EOF。

## 状态变化

prefix[i]记前i个数之和，闭区间[left,right]用两个前缀相减。

## Java 解法

```java
import java.util.*;

class Solution {
    static long[] buildPrefix(int[] nums) {
        long[] prefix = new long[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }
        return prefix;
    }

    static long rangeSum(long[] prefix, int left, int right) {
        return prefix[right + 1] - prefix[left];
    }
}
```

## 来源与版本

回放笔记Main中的同名计算函数，函数体一致；Scanner输入输出与示例数据适配在录制范围之外。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/array/Kama0058RangeSum.java

源码指纹：80b19747b5c5b904138b0ebc5dc059404edeb4e228db307a3b276ce582b067f4

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/SYdww8LbniCUBckZHRDcZ8QSnWb（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：21 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/kama-0058-range-sum/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
