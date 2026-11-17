# K44 · 卡码网 44. 开发商购买土地 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/kama-0044-land-purchase/

数组 · Land Purchase

## 题意

矩形土地划为网格，每格有价值。用一条横向或纵向的网格分界线将地块分成两个非空矩形，求两部分总价值之差的最小绝对值。

官方原题：https://kamacoder.com/problempage.php?pid=1044

## 课堂示例

输入：land = [[1, 2, 3], [2, 1, 3], [1, 2, 3]]

预期结果：0

实际结果：0

测试来源：本次数组章节实例走过程；题目链接 https://kamacoder.com/problempage.php?pid=1044

## 状态含义

rowSum、colSum是压缩后的一维数组；part是切线一侧累计和，result是最小差值。

## 状态变化

把矩阵压成行和、列和，再枚举两边都非空的切线。

## Java 解法

```java
import java.util.*;

class Solution {
    static long minDifference(long[] rowSum, long[] colSum, long total) {
        long result = Long.MAX_VALUE;
        long part = 0;
        // 最后一行之后不是合法切线，两边必须非空
        for (int row = 0; row < rowSum.length - 1; row++) {
            part += rowSum[row];
            result = Math.min(result, Math.abs(part - (total - part)));
        }
        part = 0;
        for (int col = 0; col < colSum.length - 1; col++) {
            part += colSum[col];
            result = Math.min(result, Math.abs(part - (total - part)));
        }
        return result;
    }
}
```

## 来源与版本

回放笔记Main中的同名计算函数，函数体一致；Scanner输入输出与示例数据适配在录制范围之外。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/array/Kama0044LandPurchase.java

源码指纹：d8b8db0a3185259cc7439763d38b12e23bc0c4df0917033813c55ae67e915815

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/SYdww8LbniCUBckZHRDcZ8QSnWb（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：20 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/kama-0044-land-purchase/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
