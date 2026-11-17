# 239 · 滑动窗口最大值 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0239-sliding-window-maximum/

Hot100 · 子串 · Sliding Window Maximum

## 题意

长度为 k 的窗口从数组最左端开始，每次向右移动一格。返回每个窗口的最大值。

官方原题：https://leetcode.cn/problems/sliding-window-maximum/

## 课堂示例

输入：nums=[1,3,-1,-3,5,3,6,7]; k=3

预期结果：[3,3,5,5,6,7]

实际结果：[3,3,5,5,6,7]

测试来源：总笔记同题实例

## 状态含义

deque存下标，下标递增、对应值严格递减；left=right-k+1；write是答案写入位置。

## 状态变化

队首删过期下标，队尾删不可能更优的候选，队首就是最大值。

## Java 解法

```java
import java.util.*;

class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] res = new int[nums.length - k + 1]; // 每个完整窗口对应一个答案
        int write = 0;
        Deque<Integer> deque = new ArrayDeque<>();

        for (int right = 0; right < nums.length; right++) {
            int left = right - k + 1;

            // 队首按下标判断是否已经离开窗口
            while (!deque.isEmpty() && deque.peekFirst() < left) {
                deque.pollFirst();
            }
            // 队尾按数值淘汰旧候选：新数更大或相等，且过期更晚
            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[right]) {
                deque.pollLast();
            }
            deque.offerLast(right);

            if (left >= 0) { // 窗口凑满k个元素后，才向结果数组写入
                res[write] = nums[deque.peekFirst()];
                write++;
            }
        }
        return res;
    }
}
```

## 来源与版本

沿用总笔记中本题的下标单调队列思路，写入结果与移动写指针分开表达。 真实JDI回放对应的Solution类体，算法中未加入调试输出。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/stackqueue/LC0239SlidingWindowMaximum.java

源码指纹：d4a551d3842b48283179c34a3a4d66ea60b07807130522e592e8f935ffa74903

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：81 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0239-sliding-window-maximum/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
