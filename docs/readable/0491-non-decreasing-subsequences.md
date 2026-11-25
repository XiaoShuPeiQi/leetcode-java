# 491 · 非递减子序列 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0491-non-decreasing-subsequences/

回溯 · Non-decreasing Subsequences

## 题意

给你一个整数数组 `nums` ，找出并返回所有该数组中不同的递增子序列，递增子序列中 **至少有两个元素** 。你可以按 **任意顺序** 返回答案。

数组中可能含有重复元素，如出现两个整数相等，也可以视作递增序列的一种特殊情况。

**示例 1：**

```text
输入：nums = [4,6,7,7]
输出：[[4,6],[4,6,7],[4,6,7,7],[4,7],[4,7,7],[6,7],[6,7,7],[7,7]]
```

**示例 2：**

```text
输入：nums = [4,4,3,2,1]
输出：[[4,4]]
```

**提示：**

- `1 <= nums.length <= 15`

- `-100 <= nums[i] <= 100`

官方原题：https://leetcode.cn/problems/non-decreasing-subsequences

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0400-0499/0491.Non-decreasing%20Subsequences/README.md

## 回放用例

输入：nums = [4, 7, 6, 7]

预期结果：[[4, 7], [4, 7, 7], [4, 6], [4, 6, 7], [7, 7], [6, 7]]

实际结果：[[4, 7], [4, 7, 7], [4, 6], [4, 6, 7], [7, 7], [6, 7]]

测试来源：飞书回溯笔记 3.10 的执行过程示例

## Java 解法

```java
import java.util.*;

class Solution {
    public List<List<Integer>> findSubsequences(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        backtracking(nums, 0, path, result);
        return result;
    }

    private void backtracking(int[] nums, int startIndex,
            List<Integer> path, List<List<Integer>> result) {
        // 达标就收集，仍继续找更长序列。
        if (path.size() >= 2) {
            result.add(new ArrayList<>(path));
        }
        // 每层独立记录选过的值。
        Set<Integer> levelUsed = new HashSet<>();
        for (int i = startIndex; i < nums.length; i++) {
            if (!path.isEmpty() && nums[i] < path.get(path.size() - 1)) {
                continue;
            }
            // 同层同值只展开一次。
            if (!levelUsed.add(nums[i])) {
                continue;
            }
            path.add(nums[i]);
            backtracking(nums, i + 1, path, result);
            path.remove(path.size() - 1);
        }
    }
}
```

## 来源与版本

最新飞书算法与本地导入版本一致；注释可能不同。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/backtracking/LC0491NonDecreasingSubsequences.java

源码指纹：8db4bb09485478050ca1bf8a279b388f56f81c3e409f451a586657b98ef0f43b

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/VSYBdzUkuoIxf3xuSfVcsgKkn3g（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：140 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0491-non-decreasing-subsequences/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
