# Hot100 第 52 题 · 力扣 994 · 腐烂的橘子 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0994-rotting-oranges/

Hot100 · 图论 · Rotting Oranges

## 题意

格子取值 0、1、2 分别表示空、鲜橘子和烂橘子。每分钟烂橘子会感染上下左右相邻的鲜橘子。求鲜橘子全部腐烂的最短时间，无法全部感染时返回 -1。

官方原题：https://leetcode.cn/problems/rotting-oranges/

## 课堂示例

输入：grid=[[2,1,1],[1,1,0],[0,1,1]]

预期结果：4

实际结果：4

测试来源：飞书 Hot100 原有课堂示例

## 解法

多源分层 BFS

## 状态含义

感染一个邻居时即把它设为 2，减少 `fresh` 并入队。新入队的格子从下一轮开始扩散。

## 状态变化

所有初始烂橘子同时入队，每处理一层，时间增加一分钟。

## Java 解法

```java
import java.util.*;

class Solution {
    public int orangesRotting(int[][] grid) {
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
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        while (!queue.isEmpty() && fresh > 0) {
            int size = queue.size();
            minutes++;
            while (size-- > 0) {
                int[] current = queue.poll();
                for (int[] direction : directions) {
                    int row = current[0] + direction[0];
                    int col = current[1] + direction[1];
                    if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length
                            || grid[row][col] != 1) {
                        continue;
                    }
                    grid[row][col] = 2;
                    fresh--;
                    queue.offer(new int[]{row, col});
                }
            }
        }
        return fresh == 0 ? minutes : -1;
    }
}
```

## 来源与版本

算法流程来自代码随想录的 Java 解法，保留飞书原有课堂示例。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/graph/LC0994RottingOranges.java

源码指纹：0a4117f8f69f81f4c491a8be0dd0443c30148fdb5bdabde892566ff273f4eb02

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcnVddyjnQwDHVeI6BKZX735s（外部文档可能需要登录，本文仅包含本页已发布的内容。）

代码随想录 Java 解法：https://programmercarl.com/hot100/0994.rotting-oranges.html#java

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：217 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0994-rotting-oranges/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
