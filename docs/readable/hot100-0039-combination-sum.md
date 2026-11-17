# Hot100 第 58 题 · 力扣 39 · 组合总和 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0039-combination-sum/

Hot100 · 回溯 · Combination Sum

## 题意

从互不相同的候选正整数中选数，使总和为 target。同一个数可以取多次；返回所有不同的组合，选择顺序不区分组合。

官方原题：https://leetcode.cn/problems/combination-sum/

## 课堂示例

输入：candidates = [2, 3, 6, 7], target = 7

预期结果：[[2, 2, 3], [7]]

实际结果：[[2, 2, 3], [7]]

测试来源：飞书 Hot100 原有课堂示例

## 解法

排序、累计和与回溯剪枝

## 状态含义

进入递归时携带 `sum + nums[i]`。父帧保留原 `sum`，回来只需撤销路径末尾。

## 状态变化

排序后按下标递增选数，用累计和判断何时收集、何时停止枚举。

## Java 解法

```java
import java.util.*;

class Solution {
    private List<List<Integer>> result;
    private final LinkedList<Integer> path = new LinkedList<>();

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        result = new ArrayList<>();
        Arrays.sort(candidates);
        backtracking(candidates, target, 0, 0);
        return result;
    }

    private void backtracking(int[] nums, int target, int sum, int start) {
        if (sum == target) {
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i = start; i < nums.length && sum + nums[i] <= target; i++) {
            path.add(nums[i]);
            backtracking(nums, target, sum + nums[i], i);
            path.removeLast();
        }
    }
}
```

## 来源与版本

算法流程来自代码随想录的 Java 解法，保留飞书原有课堂示例。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/backtracking/LC0039CombinationSum.java

源码指纹：40948df22490bd11f72104419a39e6b940073c77962d38889bc6cc7e947f6f7b

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcnpH2v6jvviL2iQnuBoxulsf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

代码随想录 Java 解法：https://programmercarl.com/hot100/0039.combination-sum.html#java

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：92 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0039-combination-sum/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
