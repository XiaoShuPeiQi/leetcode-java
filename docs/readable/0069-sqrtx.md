# 69 · x 的平方根 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0069-sqrtx/

数组 · Sqrtx

## 题意

给你一个非负整数 `x` ，计算并返回 `x` 的 **算术平方根** 。

由于返回类型是整数，结果只保留 **整数部分** ，小数部分将被 **舍去 。**

**注意：**不允许使用任何内置指数函数和算符，例如 `pow(x, 0.5)` 或者 `x ** 0.5` 。

**示例 1：**

```text
输入：x = 4
输出：2
```

**示例 2：**

```text
输入：x = 8
输出：2
解释：8 的算术平方根是 2.82842..., 由于返回类型是整数，小数部分将被舍去。
```

**提示：**

- `0 <= x <= 2^(31) - 1`

官方原题：https://leetcode.cn/problems/sqrtx

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0000-0099/0069.Sqrt%28x%29/README.md

## 回放用例

输入：x = 8

预期结果：2

实际结果：2

测试来源：本次数组章节实例走过程；题目链接 https://leetcode.cn/problems/sqrtx/

## 状态含义

answer是目前最大的可行整数；square必须先把mid转long再相乘。

## 状态变化

在整数答案空间里，找平方不超过x的最大整数。

## Java 解法

```java
import java.util.*;

class Solution {
    public int mySqrt(int x) {
        int left = 0;
        int right = x;
        int answer = 0;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            long square = (long) mid * mid; // 先转long，再乘
            if (square <= x) {
                answer = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return answer;
    }
}
```

## 来源与版本

Solution类体与数组笔记主解一致，只增加static、外层类和独立main。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/array/LC0069Sqrtx.java

源码指纹：c0ae0fb0e9fb15cf522ab3870a2689d99ccf76c7f67080f8d5ff10617b28f158

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/SYdww8LbniCUBckZHRDcZ8QSnWb（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：33 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0069-sqrtx/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
