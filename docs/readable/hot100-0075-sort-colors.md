# Hot100 第 98 题 · 力扣 75 · 颜色分类 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0075-sort-colors/

Hot100 · 技巧 · Sort Colors

## 题意

给定一个包含红色、白色和蓝色、共 `n` 个元素的数组 `nums` ，**原地 **对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。

我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。



必须在不使用库内置的 sort 函数的情况下解决这个问题。

**示例 1：**

输入：nums = [2,0,2,1,1,0]
输出：[0,0,1,1,2,2]
**解释：**
该数组包含两个 0、两个 1 和两个 2。将它们原地排序后，所有 0 排在最前面，接着是所有 1，最后是所有 2。

**示例 2：**

输入：nums = [2,0,1]
输出：[0,1,2]
**解释：**
数组中有且仅有一个 0、一个 1 和一个 2，按 0、1、2 的顺序原地排列。

**提示：**

- `n == nums.length`
- `1 <= n <= 300`
- `nums[i]` 为 0、1 或 2

**进阶：**

- 你能想出一个仅使用常数空间的一趟扫描算法吗？

官方原题：https://leetcode.cn/problems/sort-colors/

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0000-0099/0075.Sort%20Colors/README.md

## 回放用例

输入：nums = [2, 0, 2, 1, 1, 0]

预期结果：[0, 0, 1, 1, 2, 2]

实际结果：[0, 0, 1, 1, 2, 2]

测试来源：力扣官方示例，与本节推演一致。

## 解法

三指针原地分区

## 状态含义

left 是下一个 0 的位置，right 是下一个 2 的位置，i 是正检查的位置。i 左边已经处理过，[i,right] 还没有检查。

## 状态变化

扫描时把 0 推到左边，把 2 推到右边，1 留在中间。

## Java 解法

```java
class Solution {
    public void sortColors(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        int i = 0;

        while (i <= right) {
            if (nums[i] == 0) {
                swap(nums, i, left);
                left++;
                i++;
            } else if (nums[i] == 2) {
                swap(nums, i, right);
                right--;
            } else {
                i++;
            }
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

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/array/LC0075SortColors.java

源码指纹：86ac25c0e8aacd17c1130dbd72ce76e451e871b53cdbeb02505a4a71dbe9bd98

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcnuxGvBMpnGU7k70su0GtOyq（外部文档可能需要登录，本文仅包含本页已发布的内容。）

代码随想录 Java 解法：https://programmercarl.com/hot100/0075.sort-colors.html

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：59 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0075-sort-colors/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
