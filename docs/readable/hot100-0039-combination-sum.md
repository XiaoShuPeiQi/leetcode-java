# 39 · 组合总和 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0039-combination-sum/

Hot100 · 回溯 · Combination Sum

## 题意

从互不相同的候选正整数中选数，使总和为 target。同一个数可以取多次；返回所有不同的组合，选择顺序不区分组合。

官方原题：https://leetcode.cn/problems/combination-sum/

## 课堂示例

输入：candidates = [2, 3, 6, 7], target = 7

预期结果：[[2, 2, 3], [7]]

实际结果：[[2, 2, 3], [7]]

测试来源：算法学习总笔记与项目已有 main

## 状态含义

下标不向前回跳，避免重复排列。剩余目标为 0 时复制 path。

## 状态变化

按下标顺序选数，用剩余目标判断什么时候收集或停止。

## Java 解法

```java
import java.util.*;

class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        candidates = candidates.clone();
        Arrays.sort(candidates);
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        backtracking(candidates, target, 0, path, result);
        return result;
    }

    private void backtracking(int[] candidates, int remaining, int startIndex,
            List<Integer> path, List<List<Integer>> result) {
        if (remaining == 0) {
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i = startIndex; i < candidates.length; i++) {
            // 已排序，超出余量就停止。
            if (candidates[i] > remaining) {
                break;
            }
            path.add(candidates[i]);
            // 传 i：当前数可以重复使用。
            backtracking(candidates, remaining - candidates[i], i, path, result);
            path.remove(path.size() - 1);
        }
    }
}
```

## 来源与版本

沿用总笔记及仓库中本题的主解。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/backtracking/LC0039CombinationSum.java

源码指纹：460f45f56600f628f9163cc0241fadb38a67fb88b5c877148d0646df9033d090

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：117 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0039-combination-sum/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
