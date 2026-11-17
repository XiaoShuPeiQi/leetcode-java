# 367 · 有效的完全平方数 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0367-valid-perfect-square/

数组 · Valid Perfect Square

## 题意

判断正整数 num 能否写成某个整数的平方，不使用内置平方根函数。

官方原题：https://leetcode.cn/problems/valid-perfect-square/

## 课堂示例

输入：num = 16

预期结果：true

实际结果：true

测试来源：本次数组章节实例走过程；题目链接 https://leetcode.cn/problems/valid-perfect-square/

## 状态含义

left、right是整数根的候选范围；square用long避免中间乘法溢出。

## 状态变化

沿用平方根的单调性，但这次必须精确命中平方值。

## Java 解法

```java
import java.util.*;

class Solution {
    public boolean isPerfectSquare(int num) {
        int left = 1;
        int right = num;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            long square = (long) mid * mid;
            if (square < num) {
                left = mid + 1;
            } else if (square > num) {
                right = mid - 1;
            } else {
                return true; // 本题只接受精确命中
            }
        }
        return false;
    }
}
```

## 来源与版本

Solution类体与数组笔记主解一致，只增加static、外层类和独立main。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/array/LC0367ValidPerfectSquare.java

源码指纹：9a5f3bbd6caa6b89a68462109c8d92597cb528c3d048e92e33c2a80ca4c7f641

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/SYdww8LbniCUBckZHRDcZ8QSnWb（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：17 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0367-valid-perfect-square/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
