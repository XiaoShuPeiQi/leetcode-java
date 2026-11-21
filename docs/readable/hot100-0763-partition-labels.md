# Hot100 第 80 题 · 力扣 763 · 划分字母区间 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0763-partition-labels/

Hot100 · 贪心算法 · Partition Labels

## 题意

把字符串分成尽可能多的连续片段，使同一个字母只出现在一个片段里。按原顺序返回各片段长度。

官方原题：https://leetcode.cn/problems/partition-labels/

## 课堂示例

输入：s = "abac"

预期结果：[3, 1]

实际结果：[3, 1]

测试来源：飞书 Hot100 第 80 题「实例走过程」

## 状态含义

start 是当前段起点，end 是这一段内已扫描字母的最晚出现位置。更新 end 后，若 i == end，当前段内任何字母在后面都不会再出现，可以保存长度 i - start + 1，并将下一段起点移到 i + 1。

## 状态变化

每段右边界至少要包含已见字符的最后一次出现位置，走到这个边界就可以切一段。

## Java 解法

```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> partitionLabels(String s) {
        int[] last = new int[26];
        for (int i = 0; i < s.length(); i++) last[s.charAt(i) - 'a'] = i;
        List<Integer> result = new ArrayList<>();
        int start = 0;
        int end = 0;
        for (int i = 0; i < s.length(); i++) {
            end = Math.max(end, last[s.charAt(i) - 'a']);
            if (i == end) {
                result.add(i - start + 1);
                start = i + 1;
            }
        }
        return result;
    }
}
```

## 来源与版本

算法学习总笔记（Java）原码。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/greedy/LC0763PartitionLabels.java

源码指纹：5fc4f96c63017bb809347b003e6dc80f2485e5e607e612da546144f91488b60c

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcnSY1DUyMbYLBQTxdvEFFi9c（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：25 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0763-partition-labels/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
