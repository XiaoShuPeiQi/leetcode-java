# 53 · 最大子数组和（网站旧称最大子序和） — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0053-maximum-subarray/

贪心 · Maximum Subarray

## 题意

在非空整数数组中选一段非空连续子数组，返回它能取得的最大和。

官方原题：https://leetcode.cn/problems/maximum-subarray/

## 课堂示例

输入：nums = [-2, 1, -3, 4, -1, 2, 1]

预期结果：6

实际结果：6

测试来源：飞书贪心章节实例走过程（revision 87）

## Java 解法

```java
import java.util.*;

class Solution {
    public int maxSubArray(int[] nums) {
        int res = nums[0]; // 必须选非空子数组，全负数组也能得到正确答案
        int sum = 0;

        for (int num : nums) {
            sum += num;
            if (sum > res) {//注意顺序，这个先记录答案必须放在第一个
                res = sum;
            }
            if (sum < 0) { // 先记录答案，再丢掉会拖累后面元素的负前缀
                sum = 0;
            }
        }
        return res;
    }
}
```

## 来源与版本

同题主解按 Hot100 飞书最新版录制。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/greedy/LC0053MaximumSubarray.java

源码指纹：a26cb5440f5909535b79c8d925836ff64ed98c6add94cbed8891137440b5365b

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/QHnaw0xWDiNBTfkmaG3cbydHnYf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：40 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0053-maximum-subarray/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
