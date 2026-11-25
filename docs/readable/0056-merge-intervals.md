# 56 · 合并区间 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0056-merge-intervals/

贪心 · Merge Intervals

## 题意

以数组 `intervals` 表示若干个区间的集合，其中单个区间为 `intervals[i] = [start_i, end_i]` 。请你合并所有重叠的区间，并返回 *一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间* 。

**示例 1：**

```text
输入：intervals = [[1,3],[2,6],[8,10],[15,18]]
输出：[[1,6],[8,10],[15,18]]
解释：区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
```

**示例 2：**

```text
输入：intervals = [[1,4],[4,5]]
输出：[[1,5]]
解释：区间 [1,4] 和 [4,5] 可被视为重叠区间。
```

**示例 3：**

```text
输入：intervals = [[4,7],[1,4]]
输出：[[1,7]]
解释：区间 [1,4] 和 [4,7] 可被视为重叠区间。
```

**提示：**

- `1 <= intervals.length <= 10^4`

- `intervals[i].length == 2`

- `0 <= start_i <= end_i <= 10^4`

官方原题：https://leetcode.cn/problems/merge-intervals

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0000-0099/0056.Merge%20Intervals/README.md

## 回放用例

输入：intervals = [[1, 3], [2, 6], [8, 10], [15, 18]]

预期结果：[[1, 6], [8, 10], [15, 18]]

实际结果：[[1, 6], [8, 10], [15, 18]]

测试来源：飞书贪心章节实例走过程（revision 87）

## Java 解法

```java
import java.util.*;

class Solution {
    public int[][] merge(int[][] intervals) {
        int[][] res = new int[intervals.length][2]; // 最多产生n段，先分配足够的结果位置
        int count = 0; // res中已经写好的区间个数
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        for (int[] current : intervals) {
            if (count == 0 || current[0] > res[count - 1][1]) {
                res[count][0] = current[0]; // 没有重叠，写入一段新的区间
                res[count][1] = current[1];
                count++;
            } else {
                if (current[1] > res[count - 1][1]) { // 有重叠，只需要延长最后一段的右端
                    res[count - 1][1] = current[1];
                }
            }
        }

        res = Arrays.copyOf(res, count); // 只保留已经写入的count段，去掉未使用的位置
        return res;
    }
}
```

## 来源与版本

同题主解按 Hot100 飞书最新版录制。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/greedy/LC0056MergeIntervals.java

源码指纹：1ffcfdaf0b9e8e50f894400c532ba4ca4970dcfcc02a1f45aa94cf501f9af682

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/QHnaw0xWDiNBTfkmaG3cbydHnYf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：36 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0056-merge-intervals/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
