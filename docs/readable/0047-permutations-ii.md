# 47 · 全排列 II — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0047-permutations-ii/

回溯 · Permutations II

## 题意

给定可含重复值的整数数组，返回用全部元素排成的所有不同序列。

官方原题：https://leetcode.cn/problems/permutations-ii/

## 课堂示例

输入：nums = [1, 1, 2]

预期结果：[[1, 1, 2], [1, 2, 1], [2, 1, 1]]

实际结果：[[1, 1, 2], [1, 2, 1], [2, 1, 1]]

测试来源：飞书回溯笔记 3.12 的执行过程示例

## Java 解法

```java
import java.util.*;

class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        nums = nums.clone();
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        boolean[] used = new boolean[nums.length];
        List<Integer> path = new ArrayList<>();
        backtracking(nums, used, path, result);
        return result;
    }

    private void backtracking(int[] nums, boolean[] used,
            List<Integer> path, List<List<Integer>> result) {
        if (path.size() == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) {
                continue;
            }
            // 前一个同值未使用时，跳过重复分支。
            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
                continue;
            }
            used[i] = true;
            path.add(nums[i]);
            backtracking(nums, used, path, result);
            path.remove(path.size() - 1);
            used[i] = false;
        }
    }
}
```

## 来源与版本

最新飞书算法与本地导入版本一致；注释可能不同。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/backtracking/LC0047PermutationsII.java

源码指纹：32f4540099810b6f93978bd37955995c6cb51c4bb1771d87a5dd8b23d721ca20

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/VSYBdzUkuoIxf3xuSfVcsgKkn3g（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：150 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0047-permutations-ii/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
