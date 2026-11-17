# 332 · 重新安排行程 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0332-reconstruct-itinerary/

回溯 · Reconstruct Itinerary

## 题意

每张机票 [from, to] 指定一次航程。由 JFK 出发且每张机票恰用一次，返回行程；多种可行行程取字典序最小的。

官方原题：https://leetcode.cn/problems/reconstruct-itinerary/

## 课堂示例

输入：tickets = [[JFK, KUL], [JFK, NRT], [NRT, JFK]]

预期结果：[JFK, NRT, JFK, KUL]

实际结果：[JFK, NRT, JFK, KUL]

测试来源：飞书回溯笔记 3.13 的执行过程示例

## Java 解法

```java
import java.util.*;

class Solution {
    public List<String> findItinerary(List<List<String>> tickets) {
        Map<String, PriorityQueue<String>> graph = new HashMap<>();
        for (List<String> ticket : tickets) {
            String from = ticket.get(0);
            String to = ticket.get(1);
            if (!graph.containsKey(from)) {
                graph.put(from, new PriorityQueue<>());
            }
            PriorityQueue<String> destinations = graph.get(from);
            destinations.offer(to);
        }
        List<String> result = new ArrayList<>();
        traverse("JFK", graph, result);
        Collections.reverse(result);
        return result;
    }

    private void traverse(String airport, Map<String, PriorityQueue<String>> graph,
            List<String> result) {
        PriorityQueue<String> destinations = graph.get(airport);
        while (destinations != null && !destinations.isEmpty()) {
            // 先取字典序最小的剩余机票。
            String nextAirport = destinations.poll();
            traverse(nextAirport, graph, result);
        }
        // 无出边时收集，最后反转。
        result.add(airport);
    }
}
```

## 来源与版本

最新飞书算法与本地导入版本一致；注释可能不同。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/backtracking/LC0332ReconstructItinerary.java

源码指纹：658aa62aa390b962db74e9dbeb2d021e95a96894dbe57039e3ea59ee7b6a5704

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/VSYBdzUkuoIxf3xuSfVcsgKkn3g（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：64 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0332-reconstruct-itinerary/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
