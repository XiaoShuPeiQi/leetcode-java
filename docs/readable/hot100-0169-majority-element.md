# Hot100 第 97 题 · 力扣 169 · 多数元素 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0169-majority-element/

Hot100 · 技巧 · Majority Element

## 题意

给定一个大小为 `n` 的数组 `nums` ，返回其中的多数元素。多数元素是指在数组中出现次数 **大于** `⌊ n/2 ⌋` 的元素。

你可以假设数组是非空的，并且给定的数组总是存在多数元素。

**示例 1：**

```text
输入：nums = [3,2,3]
输出：3
```

**示例 2：**

```text
输入：nums = [2,2,1,1,1,2,2]
输出：2
```

- `n == nums.length`
- `1 <= n <= 5 * 104`
- `-109 <= nums[i] <= 109`
- 输入保证数组中一定有一个多数元素。

**进阶：**尝试设计时间复杂度为 O(n)、空间复杂度为 O(1) 的算法解决此问题。

官方原题：https://leetcode.cn/problems/majority-element/

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0100-0199/0169.Majority%20Element/README.md

## 回放用例

输入：nums = [2, 2, 1, 1, 1, 2, 2]

预期结果：2

实际结果：2

测试来源：力扣官方示例，与本节推演一致。

## 解法

摩尔投票

## 状态含义

candidate 是当前候选，votes 是还没被抵消的票数。票数为 0 时用当前值接任。与候选相同加一票，不同减一票。

## 状态变化

不同的两个数互相抵消，出现超过一半的数最后一定还有剩余。

## Java 解法

```java
class Solution {
    public int majorityElement(int[] nums) {
        int candidate = 0;
        int votes = 0;
        for (int num : nums) {
            if (votes == 0) {
                candidate = num;
            }
            votes += num == candidate ? 1 : -1;
        }
        return candidate;
    }
}
```

## 来源与版本

依据代码随想录的解法思路整理 Java 版。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/array/LC0169MajorityElement.java

源码指纹：1416864704517c0e2b205a3a2f82192b9bf74eadc673d0d8ce16ea39ec90430e

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcnn6cLUaOZH6Lpprh7iEkxic（外部文档可能需要登录，本文仅包含本页已发布的内容。）

代码随想录 Java 解法：https://programmercarl.com/hot100/0169.majority-element.html

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：30 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0169-majority-element/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
