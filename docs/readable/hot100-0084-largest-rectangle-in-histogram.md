# Hot100 第 73 题 · 力扣 84 · 柱状图中最大的矩形 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0084-largest-rectangle-in-histogram/

Hot100 · 栈 · Largest Rectangle In Histogram

## 题意

给定 *n* 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。

求在该柱状图中，能够勾勒出来的矩形的最大面积。

**示例 1:**

![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0000-0099/0084.Largest%20Rectangle%20in%20Histogram/images/histogram.jpg)

```text
输入：heights = [2,1,5,6,2,3]
输出：10
解释：最大的矩形为图中红色区域，面积为 10
```

**示例 2：**

![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0000-0099/0084.Largest%20Rectangle%20in%20Histogram/images/histogram-1.jpg)

```text
输入： heights = [2,4]
输出： 4
```

**提示：**

- `1 <= heights.length <=10^5`

- `0 <= heights[i] <= 10^4`

官方原题：https://leetcode.cn/problems/largest-rectangle-in-histogram

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0000-0099/0084.Largest%20Rectangle%20in%20Histogram/README.md

## 回放用例

输入：heights = [2, 1, 5, 6, 2, 3]

预期结果：10

实际结果：10

测试来源：飞书 Hot100 第 73 题「实例走过程」

## 解法

较矮的柱子出现时，结算被它挡住的较高柱子能撑出的矩形面积。

## 状态含义

栈底到栈顶对应高度单调不减。每次弹出的 mid 提供高度，宽度是两个边界之间的柱子数 i - stack.peek() - 1。相同高度会共同留栈，靠右的等高柱先结算，靠左的等高柱随后能覆盖更宽的区间。

## 状态变化

较矮的柱子出现时，结算被它挡住的较高柱子能撑出的矩形面积。

## Java 解法

```java
import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public int largestRectangleArea(int[] heights) {
        int[] newHeights = new int[heights.length + 2];
        System.arraycopy(heights, 0, newHeights, 1, heights.length);

        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(0);
        int result = 0;

        for (int i = 1; i < newHeights.length; i++) {
            while (newHeights[i] < newHeights[stack.peek()]) {
                int mid = stack.pop();
                int width = i - stack.peek() - 1;
                result = Math.max(result, newHeights[mid] * width);
            }
            stack.push(i);
        }

        return result;
    }
}
```

## 来源与版本

代码随想录 · Java。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/stackqueue/LC0084LargestRectangleInHistogram.java

源码指纹：379fcb800f1c95353cfd5d9a46e5493ecb1eae81316b996f42ebbe4a6308e20a

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcnnz8UCX2ugRMDueI35zL17b（外部文档可能需要登录，本文仅包含本页已发布的内容。）

代码随想录 Java 解法：https://programmercarl.com/hot100/0084.largest-rectangle-in-histogram.html

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：60 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0084-largest-rectangle-in-histogram/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
