# Hot100 第 56 题 · 力扣 78 · 子集 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0078-subsets/

Hot100 · 回溯 · Subsets

## 题意

给定元素互不相同的整数数组，返回其所有子集，包括空集和全集。结果中每个子集只出现一次。

官方原题：https://leetcode.cn/problems/subsets/

## 课堂示例

输入：nums = [1, 2]

预期结果：[[], [1], [1, 2], [2]]

实际结果：[[], [1], [1, 2], [2]]

测试来源：飞书 Hot100 原有课堂示例

## 解法

起点下标递增的回溯

## 状态含义

同一条路径上的下标严格递增，因此每组下标只会访问一次。根节点的空路径也要收集。

## 状态变化

递归树上的每个节点对应一个子集，进入本层就收集当前路径。

## Java 解法

```java
import java.util.*;

class Solution {
    private List<List<Integer>> result;
    private final LinkedList<Integer> path = new LinkedList<>();

    public List<List<Integer>> subsets(int[] nums) {
        result = new ArrayList<>();
        backtracking(nums, 0);
        return result;
    }

    private void backtracking(int[] nums, int start) {
        result.add(new ArrayList<>(path));
        for (int i = start; i < nums.length; i++) {
            path.add(nums[i]);
            backtracking(nums, i + 1);
            path.removeLast();
        }
    }
}
```

## 来源与版本

算法流程来自代码随想录的 Java 解法，保留飞书原有课堂示例。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/backtracking/LC0078Subsets.java

源码指纹：77eef2f98e365740e3ffbb1c16e24f2126dcc47dd5adf4b6b2ebe435cfe20fb6

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcneOsHpYBOC5jOg7Okd4x2Ze（外部文档可能需要登录，本文仅包含本页已发布的内容。）

代码随想录 Java 解法：https://programmercarl.com/hot100/0078.subsets.html#java

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：37 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0078-subsets/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
