# 452 · 用最少数量的箭引爆气球 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0452-minimum-number-of-arrows-to-burst-balloons/

贪心 · Minimum Number Of Arrows To Burst Balloons

## 题意

每个气球在横轴上覆盖一个闭区间。一支竖直射出的箭可引爆其横坐标落在覆盖范围内的所有气球。求引爆全部气球所需的最少箭数。

官方原题：https://leetcode.cn/problems/minimum-number-of-arrows-to-burst-balloons/

## 课堂示例

输入：points = [[10, 16], [2, 8], [1, 6], [7, 12]]

预期结果：2

实际结果：2

测试来源：飞书贪心章节实例走过程（revision 87）

## Java 解法

```java
import java.util.*;

class Solution {
    public int findMinArrowShots(int[][] points) {
        if (points.length == 0) return 0;
        points = points.clone();
        Arrays.sort(points, (a, b) -> Integer.compare(a[1], b[1]));
        int arrow = points[0][1];
        int result = 1;
        for (int i = 1; i < points.length; i++) {
            if (points[i][0] > arrow) {
                result++;
                arrow = points[i][1];
            }
        }
        return result;
    }
}
```

## 来源与版本

录制代码与2026-10-14读取的飞书Solution类体一致，只增加static与外层运行适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/greedy/LC0452MinimumNumberOfArrowsToBurstBalloons.java

源码指纹：c31c4c03e37fdaa9f1469de3f88978148f42fc8b5eeadb84c00c1108774e6e4e

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/QHnaw0xWDiNBTfkmaG3cbydHnYf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：32 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0452-minimum-number-of-arrows-to-burst-balloons/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
