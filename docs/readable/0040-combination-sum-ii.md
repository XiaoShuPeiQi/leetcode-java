# 40 · 组合总和 II — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0040-combination-sum-ii/

回溯 · Combination Sum II

## 题意

从 candidates 中选取若干元素，使总和为 target。每个数组位置最多取一次；按值去重后返回所有组合。

官方原题：https://leetcode.cn/problems/combination-sum-ii/

## 课堂示例

输入：candidates = [1, 1, 2], target = 2

预期结果：[[1, 1], [2]]

实际结果：[[1, 1], [2]]

测试来源：飞书回溯笔记 3.5 的执行过程示例

## Java 解法

```java
import java.util.*;

class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
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
            // 同层去重。
            if (i > startIndex && candidates[i] == candidates[i - 1]) {
                continue;
            }
            if (candidates[i] > remaining) {
                break;
            }
            path.add(candidates[i]);
            // 传 i + 1：当前下标不再使用。
            backtracking(candidates, remaining - candidates[i], i + 1, path, result);
            path.remove(path.size() - 1);
        }
    }
}
```

## 来源与版本

最新飞书算法与本地导入版本一致；注释可能不同。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/backtracking/LC0040CombinationSumII.java

源码指纹：978ea4fd32c72b10086f08ec67342c0e45b02219599b4a69e76637565d93941d

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/VSYBdzUkuoIxf3xuSfVcsgKkn3g（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：52 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0040-combination-sum-ii/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
