# 78 · 子集 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0078-subsets/

Hot100 · 回溯 · Subsets

## 题意

给定元素互不相同的整数数组，返回其所有子集，包括空集和全集。结果中每个子集只出现一次。

官方原题：https://leetcode.cn/problems/subsets/

## 课堂示例

输入：nums = [1, 2]

预期结果：[[], [1], [1, 2], [2]]

实际结果：[[], [1], [1, 2], [2]]

测试来源：算法学习总笔记与项目已有 main

## 状态含义

沿一条路径走时，下标严格递增，同一组元素只会按一种顺序被选择。

## 状态变化

每次只从后面的下标选，递归树上的每个路径都是一个子集。

## Java 解法

```java
import java.util.*;

class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        backtracking(nums, 0, path, result);
        return result;
    }

    private void backtracking(int[] nums, int startIndex,
            List<Integer> path, List<List<Integer>> result) {
        // 每个节点都收集，包括空集。
        result.add(new ArrayList<>(path));
        for (int i = startIndex; i < nums.length; i++) {
            path.add(nums[i]);
            backtracking(nums, i + 1, path, result);
            path.remove(path.size() - 1);
        }
    }
}
```

## 来源与版本

沿用总笔记及仓库中本题的主解。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/backtracking/LC0078Subsets.java

源码指纹：075abdbad496ccaf4effddb53e94294159a67a51b67c4e7bf7d1914b7a4a5251

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：38 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0078-subsets/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
