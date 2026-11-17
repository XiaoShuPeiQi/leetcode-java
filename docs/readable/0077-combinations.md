# 77 · 组合 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0077-combinations/

回溯 · Combinations

## 题意

从整数 1 到 n 中选 k 个不同的数，返回所有组合。同一批数字只算一次。

官方原题：https://leetcode.cn/problems/combinations/

## 课堂示例

输入：n = 4, k = 2

预期结果：[[1, 2], [1, 3], [1, 4], [2, 3], [2, 4], [3, 4]]

实际结果：[[1, 2], [1, 3], [1, 4], [2, 3], [2, 4], [3, 4]]

测试来源：飞书回溯笔记 3.1 的执行过程示例

## Java 解法

```java
import java.util.*;

class Solution {
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        backtracking(n, k, 1, path, result);
        return result;
    }

    private void backtracking(int n, int k, int startNum,
            List<Integer> path, List<List<Integer>> result) {
        if (path.size() == k) {
            result.add(new ArrayList<>(path));
            return;
        }
        int need = k - path.size();
        // 数量剪枝：后面至少留够 need 个数。
        int lastStartIndex = n - need + 1;
        for (int i = startNum; i <= lastStartIndex; i++) {
            path.add(i);
            backtracking(n, k, i + 1, path, result);
            path.remove(path.size() - 1);
        }
    }
}
```

## 来源与版本

最新飞书算法与本地导入版本一致；注释可能不同。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/backtracking/LC0077Combinations.java

源码指纹：98c2fc1dbfed3dc7a14af85a89e3a090cf6627647375a80475154f97902fbe74

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/VSYBdzUkuoIxf3xuSfVcsgKkn3g（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：100 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0077-combinations/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
