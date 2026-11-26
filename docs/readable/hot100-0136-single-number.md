# Hot100 第 96 题 · 力扣 136 · 只出现一次的数字 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0136-single-number/

Hot100 · 技巧 · Single Number

## 题意

给你一个 **非空** 整数数组 `nums` ，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。

你必须设计并实现线性时间复杂度的算法来解决此问题，且该算法只使用常量额外空间。

**示例 1 ：**

**输入：**nums = [2,2,1]
**输出：**1

**示例 2 ：**

**输入：**nums = [4,1,2,1,2]
**输出：**4

**示例 3 ：**

**输入：**nums = [1]
**输出：**1

 
**提示：**

`1 <= nums.length <= 3 * 104`
`-3 * 104 <= nums[i] <= 3 * 104`
除了某个元素只出现一次以外，其余每个元素均出现两次。

官方原题：https://leetcode.cn/problems/single-number/

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0100-0199/0136.Single%20Number/README.md

## 回放用例

输入：nums = [4, 1, 2, 1, 2]

预期结果：4

实际结果：4

测试来源：力扣官方示例，与本节推演一致。

## 解法

异或抵消

## 状态含义

result 是已扫描前缀的异或值。x ^ x 等于 0，x ^ 0 等于 x，异或可以交换和重新分组。

## 状态变化

从 0 开始把所有数异或一遍，出现两次的数成对抵消。

## Java 解法

```java
class Solution {
    public int singleNumber(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result ^= num;
        }
        return result;
    }
}
```

## 来源与版本

依据代码随想录的解法思路整理 Java 版。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/array/LC0136SingleNumber.java

源码指纹：ebcc22fdbe66ac4ac106de5c20cf10d28088c427d56648f84fc93ce1bda1b2e5

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcn21A12Sh0xDaTEfBWU6hAle（外部文档可能需要登录，本文仅包含本页已发布的内容。）

代码随想录 Java 解法：https://programmercarl.com/hot100/0136.single-number.html

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：15 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0136-single-number/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
