# Hot100 第 53 题 · 力扣 207 · 课程表 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0207-course-schedule/

Hot100 · 图论 · Course Schedule

## 题意

共有 numCourses 门课，每个先修条件 [a, b] 表示学 a 之前必须学 b。判断能否安排一个修完全部课程的顺序。

官方原题：https://leetcode.cn/problems/course-schedule/

## 课堂示例

输入：numCourses=4, prerequisites=[[1,0],[2,0],[3,1],[3,2]]

预期结果：true

实际结果：true

测试来源：飞书 Hot100 原有课堂示例

## 解法

入度表与队列拓扑排序

## 状态含义

`--inDegree[next] == 0` 表示最后一个要求刚被满足，此时 `next` 才能入队。

## 状态变化

先学习入度为 0 的课，每学完一门，就消掉它对后续课程的一项先修要求。

## Java 解法

```java
import java.util.*;

class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        int[] inDegree = new int[numCourses];
        for (int[] edge : prerequisites) {
            graph.get(edge[1]).add(edge[0]);
            inDegree[edge[0]]++;
        }

        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        int learned = 0;
        while (!queue.isEmpty()) {
            int current = queue.poll();
            learned++;
            for (int next : graph.get(current)) {
                if (--inDegree[next] == 0) {
                    queue.offer(next);
                }
            }
        }
        return learned == numCourses;
    }
}
```

## 来源与版本

算法流程来自代码随想录的 Java 解法，保留飞书原有课堂示例。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/graph/LC0207CourseSchedule.java

源码指纹：9cbb5c0e2d0da98e98686836f50058b9ea5744a7a711a79361b38af4ddbd235c

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcndnAZs1S4EjUk5TJUdqUyde（外部文档可能需要登录，本文仅包含本页已发布的内容。）

代码随想录 Java 解法：https://programmercarl.com/hot100/0207.course-schedule.html#java

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：71 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0207-course-schedule/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
