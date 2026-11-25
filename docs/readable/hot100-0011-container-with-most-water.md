# 11 · 盛最多水的容器 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0011-container-with-most-water/

Hot100 · 双指针 · Container With Most Water

## 题意

给定一个长度为 `n` 的整数数组 `height` 。有 `n` 条垂线，第 `i` 条线的两个端点是 `(i, 0)` 和 `(i, height[i])` 。

找出其中的两条线，使得它们与 `x` 轴共同构成的容器可以容纳最多的水。

返回容器可以储存的最大水量。

**说明：**你不能倾斜容器。

**示例 1：**

![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0000-0099/0011.Container%20With%20Most%20Water/images/question_11.jpg)

```text
输入：[1,8,6,2,5,4,8,3,7]
输出：49
解释：图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。
```

**示例 2：**

```text
输入：height = [1,1]
输出：1
```

**提示：**

- `n == height.length`

- `2 <= n <= 10^5`

- `0 <= height[i] <= 10^4`

官方原题：https://leetcode.cn/problems/container-with-most-water

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0000-0099/0011.Container%20With%20Most%20Water/README.md

## 回放用例

输入：height=[1,8,6,2,5,4,8,3,7]

预期结果：49

实际结果：49

测试来源：力扣公开示例（已读取题目与Java模板）

## 状态含义

left、right是两块板的下标；res保存已经检查过的最大面积。

## 状态变化

先算当前两端面积，再移动较短的那一侧。

## Java 解法

```java
import java.util.*;

class Solution {
    public int maxArea(int[] height) {
        int res = 0; // 保存最大容积
        int left = 0;
        int right = height.length - 1;

        while (left < right) {
            int area = Math.min(height[left], height[right]) * (right - left);
            if (area > res) {
                res = area;
            }

            // 面积受短板限制，移动短板才可能找到更大容积
            if (height[left] <= height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return res;
    }
}
```

## 来源与版本

学习扩展：总笔记未检出本题完整题解，按已有章节的方法组织新解法。 真实JDI回放对应的Solution类体，算法中未加入调试输出。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/array/LC0011ContainerWithMostWater.java

源码指纹：1915ef0957f1286f61e3cdf0ba991c54232a999b98a0d0ecec0ac31e343f0332

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：57 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0011-container-with-most-water/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
