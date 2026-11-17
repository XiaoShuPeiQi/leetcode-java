# 128 · 最长连续序列 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0128-longest-consecutive-sequence/

Hot100 · 哈希 · Longest Consecutive Sequence

## 题意

在无序整数数组中求最长的连续整数序列的长度。序列里的值彼此相差 1，它们在原数组中可以不挨着。要求 O(n) 时间。

官方原题：https://leetcode.cn/problems/longest-consecutive-sequence/

## 课堂示例

输入：nums=[100,4,200,1,3,2]

预期结果：4

实际结果：4

测试来源：力扣公开示例（已读取题目与Java模板）

## 状态含义

set存不同数；num先表示候选起点，确认没有前驱后直接向后递增，表示本段当前末尾；length是本段长度；res是最长长度。修改num只改变本轮局部变量，不会修改set中的元素。

## 状态变化

只从没有前驱的数出发，向右数完整段。

## Java 解法

```java
import java.util.*;

class Solution {
    public int longestConsecutive(int[] nums) {
        int res = 0; // 保存已经找到的最长连续段长度
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        for (int num : set) {
            if (set.contains(num - 1)) { // 有前驱就不是起点，避免重复扫描同一段
                continue;
            }

            int length = 1;
            while (set.contains(num + 1)) {
                length++;
                num++;
            }
            if (length > res) {
                res = length;
            }
        }
        return res;
    }
}
```

## 来源与版本

学习扩展：总笔记未检出本题完整题解，按已有章节的方法组织新解法。 真实JDI回放对应的Solution类体，算法中未加入调试输出。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/hashtable/LC0128LongestConsecutiveSequence.java

源码指纹：3fbdd224c4bde657e79438cc2d120afa3f9e83d1dd3f5b62be29267b5222c332

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：50 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0128-longest-consecutive-sequence/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
