# 376 · 摆动序列 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0376-wiggle-subsequence/

贪心 · Wiggle Subsequence

## 题意

从 nums 按原顺序选出一个子序列，使相邻两项的差非零且正负交替。返回最大的子序列长度。

官方原题：https://leetcode.cn/problems/wiggle-subsequence/

## 课堂示例

输入：nums = [1, 2, 2, 3, 1]

预期结果：3

实际结果：3

测试来源：飞书贪心章节实例走过程（revision 87）

## Java 解法

```java
import java.util.*;

class Solution {
    public int wiggleMaxLength(int[] nums) {
        if (nums.length < 2) return nums.length;
        int preDiff = 0;
        int result = 1;
        for (int i = 1; i < nums.length; i++) {
            int curDiff = nums[i] - nums[i - 1];
            if ((curDiff > 0 && preDiff <= 0) || (curDiff < 0 && preDiff >= 0)) {
                result++;
                preDiff = curDiff; // 平坡、连续同向都保留上一段方向
            }
        }
        return result;
    }
}
```

## 来源与版本

录制代码与2026-10-14读取的飞书Solution类体一致，只增加static与外层运行适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/greedy/LC0376WiggleSubsequence.java

源码指纹：cac80308c893bf304a01619e9b764afc82cd4afdd04fb3d47a1217187e13675e

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/QHnaw0xWDiNBTfkmaG3cbydHnYf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：23 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0376-wiggle-subsequence/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
