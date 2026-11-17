# 216 · 组合总和 III — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0216-combination-sum-iii/

回溯 · Combination Sum III

## 题意

从 1 到 9 中选 k 个不同的数，使总和为 n。返回所有符合条件的组合，每组只出现一次。

官方原题：https://leetcode.cn/problems/combination-sum-iii/

## 课堂示例

输入：k = 3, n = 7

预期结果：[[1, 2, 4]]

实际结果：[[1, 2, 4]]

测试来源：飞书回溯笔记 3.2 的执行过程示例

## Java 解法

```java
import java.util.*;

class Solution {
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        backtracking(k, n, 1, path, result);
        return result;
    }

    private void backtracking(int k, int remaining, int startIndex,
            List<Integer> path, List<List<Integer>> result) {
        // 选满 k 个后，只检查和是否满足。
        if (path.size() == k) {
            if (remaining == 0) {
                result.add(new ArrayList<>(path));
            }
            return;
        }
        int need = k - path.size();
        int lastStartIndex = 9 - need + 1;
        for (int i = startIndex; i <= lastStartIndex; i++) {
            // 后面的数更大，可以直接停止。
            if (i > remaining) {
                break;
            }
            path.add(i);
            backtracking(k, remaining - i, i + 1, path, result);
            path.remove(path.size() - 1);
        }
    }
}
```

## 来源与版本

最新飞书算法与本地导入版本一致；注释可能不同。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/backtracking/LC0216CombinationSumIII.java

源码指纹：db1ac47156b7190371a026dd8aa0df33ee61c9538ffa6c6d11b782dc3c685ab6

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/VSYBdzUkuoIxf3xuSfVcsgKkn3g（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：258 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0216-combination-sum-iii/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
