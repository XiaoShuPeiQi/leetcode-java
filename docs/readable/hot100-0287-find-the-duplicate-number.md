# Hot100 第 100 题 · 力扣 287 · 寻找重复数 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0287-find-the-duplicate-number/

Hot100 · 技巧 · Find the Duplicate Number

## 题意

给定一个包含 `n + 1` 个整数的数组 `nums` ，其数字都在 `[1, n]` 范围内（包括 `1` 和 `n`），可知至少存在一个重复的整数。

假设 `nums` 只有 **一个重复的整数** ，返回 **这个重复的数** 。

你设计的解决方案必须 **不修改** 数组 `nums` 且只用常量级 `O(1)` 的额外空间。

**示例 1：**

```text
输入：nums = [1,3,4,2,2]
输出：2
```

**示例 2：**

```text
输入：nums = [3,1,3,4,2]
输出：3
```

**示例 3 :**

```text
输入：nums = [3,3,3,3,3]
输出：3
```

**提示：**

- `1 <= n <= 105`
- `nums.length == n + 1`
- `1 <= nums[i] <= n`
- `nums` 中 **只有一个整数** 出现 **两次或多次** ，其余整数均只出现 **一次**

进阶：

- 如何证明 `nums` 中至少存在一个重复的数字?
- 你可以设计一个线性级时间复杂度 `O(n)` 的解决方案吗？

官方原题：https://leetcode.cn/problems/find-the-duplicate-number/

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0200-0299/0287.Find%20the%20Duplicate%20Number/README.md

## 回放用例

输入：nums = [1, 3, 4, 2, 2]

预期结果：2

实际结果：2

测试来源：力扣官方示例，与本节推演一致。

## 解法

快慢指针找环入口

## 状态含义

第一阶段 slow 每次跳一步，fast 每次跳两步。相遇后把 slow 放回 0，两个指针都改成一步。起点到入口的距离，和相遇点顺着环走到入口的距离相差整圈。再次相遇时就是入口。

## 状态变化

把 nums[i] 看成从下标 i 跳向的下一站，用快慢指针找到环入口。

## Java 解法

```java
class Solution {
    public int findDuplicate(int[] nums) {
        int slow = 0;
        int fast = 0;
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);

        slow = 0;
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }
}
```

## 来源与版本

依据代码随想录的解法思路整理 Java 版。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/array/LC0287FindTheDuplicateNumber.java

源码指纹：ddadb931832a85573e7e1b3aa01fa7dd2ceee5159d8d9ae3f403f59d4beb9f3b

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcniAZyJke9VVlfiQ6amQvypd（外部文档可能需要登录，本文仅包含本页已发布的内容。）

代码随想录 Java 解法：https://programmercarl.com/hot100/0287.find-the-duplicate-number.html

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：28 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0287-find-the-duplicate-number/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
