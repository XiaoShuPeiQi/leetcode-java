# 1005 · K 次取反后最大化的数组和 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/1005-maximize-sum-of-array-after-k-negations/

贪心 · Maximize Sum Of Array After K Negations

## 题意

恰好做 k 次操作，每次选择一个下标并把它的值取反，同一下标可重复选择。求操作后数组和的最大值。

官方原题：https://leetcode.cn/problems/maximize-sum-of-array-after-k-negations/

## 课堂示例

输入：nums = [-4, -2, 3], k = 3

预期结果：5

实际结果：5

测试来源：飞书贪心章节实例走过程（revision 87）

## Java 解法

```java
import java.util.*;

class Solution {
    public int largestSumAfterKNegations(int[] nums, int k) {
        nums = nums.clone();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length && nums[i] < 0 && k > 0; i++) {
            nums[i] = -nums[i];
            k--;
        }
        int sum = 0;
        int minIndex = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (Math.abs(nums[i]) < Math.abs(nums[minIndex])) minIndex = i;
        }
        if (k % 2 == 1) sum -= 2 * nums[minIndex]; // 两次取反抵消，只处理余下的一次
        return sum;
    }
}
```

## 来源与版本

录制代码与2026-10-14读取的飞书Solution类体一致，只增加static与外层运行适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/greedy/LC1005MaximizeSumOfArrayAfterKNegations.java

源码指纹：85d3742a87e3f456a3fd0ae9d8f8a4f9644a88ef4b6bc988acccf1ac7e5c5da4

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/QHnaw0xWDiNBTfkmaG3cbydHnYf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：25 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/1005-maximize-sum-of-array-after-k-negations/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
