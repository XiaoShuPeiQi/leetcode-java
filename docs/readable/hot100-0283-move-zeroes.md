# 283 · 移动零 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0283-move-zeroes/

Hot100 · 双指针 · Move Zeroes

## 题意

给定一个数组 `nums`，编写一个函数将所有 `0` 移动到数组的末尾，同时保持非零元素的相对顺序。

**请注意** ，必须在不复制数组的情况下原地对数组进行操作。

**示例 1:**

```text
输入: nums = [0,1,0,3,12]
输出: [1,3,12,0,0]
```

**示例 2:**

```text
输入: nums = [0]
输出: [0]
```

**提示**:

- `1 <= nums.length <= 10^4`

- `-2^(31) <= nums[i] <= 2^(31) - 1`

**进阶：**你能尽量减少完成的操作次数吗？

官方原题：https://leetcode.cn/problems/move-zeroes

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0200-0299/0283.Move%20Zeroes/README.md

## 回放用例

输入：nums=[0,1,0,3,12]

预期结果：[1, 3, 12, 0, 0]

实际结果：[1, 3, 12, 0, 0]

测试来源：总笔记同题实例

## 状态含义

fast是读位置；slow既是非零数个数，也是下一次写入的位置。方法返回void，答案在nums中。

## 状态变化

先稳定收集非零元素，再把剩余位置补零。

## Java 解法

```java
import java.util.*;

class Solution {
    public void moveZeroes(int[] nums) {
        // 结果直接写回nums：先收集非零元素，再在剩余位置补零
        int slow = 0;
        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != 0) {
                nums[slow] = nums[fast];
                slow++;
            }
        }
        // 有效前缀之外必须全部补零
        for (int i = slow; i < nums.length; i++) {
            nums[i] = 0;
        }
    }
}
```

## 来源与版本

沿用总笔记中本题的算法思路，代码按结果先定义、逐步填入的方式展开。 真实JDI回放对应的Solution类体，算法中未加入调试输出。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/array/LC0283MoveZeroes.java

源码指纹：4230f48c28a8dc5d333c1103698a6575374050b8381726c70eb5eace898c81d0

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：26 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0283-move-zeroes/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
