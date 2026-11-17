# 994 · 腐烂的橘子 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0994-rotting-oranges/

Hot100 · 图论 · Rotting Oranges

## 题意

格子取值 0、1、2 分别表示空、鲜橘子和烂橘子。每分钟烂橘子会感染上下左右相邻的鲜橘子。求鲜橘子全部腐烂的最短时间，无法全部感染时返回 -1。

官方原题：https://leetcode.cn/problems/rotting-oranges/

## 课堂示例

输入：grid=[[2,1,1],[1,1,0],[0,1,1]]

预期结果：4

实际结果：4

测试来源：力扣公开示例

## 状态含义

size 锁定本轮扩展数，新橘子一入队就改为 2。

## 状态变化

让所有烂橘子一同出发，队列每处理一层就过去一分钟。

## Java 解法

```java
import java.util.*;

class Solution {
    public int orangesRotting(int[][] grid) {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        Queue<int[]> queue = new ArrayDeque<>();
        int fresh = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] == 2) {
                    queue.offer(new int[]{row, col});
                } else if (grid[row][col] == 1) {
                    fresh++;
                }
            }
        }

        int minutes = 0;
        while (!queue.isEmpty() && fresh > 0) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] cell = queue.poll();
                for (int[] direction : directions) {
                    int nextRow = cell[0] + direction[0];
                    int nextCol = cell[1] + direction[1];
                    if (nextRow < 0 || nextRow >= grid.length
                            || nextCol < 0 || nextCol >= grid[0].length) {
                        continue;
                    }
                    if (grid[nextRow][nextCol] == 1) {
                        grid[nextRow][nextCol] = 2; // 入队时就标记
                        fresh--;
                        queue.offer(new int[]{nextRow, nextCol});
                    }
                }
            }
            minutes++;
        }
        return fresh == 0 ? minutes : -1;
    }
}
```

## 来源与版本

学习扩展。按本题条件选择主解。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/graph/LC0994RottingOranges.java

源码指纹：705e0784791edea87f048a3d4a9a57fa26cab5c389de3d2034cd28bf0f36c8b5

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：218 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0994-rotting-oranges/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
