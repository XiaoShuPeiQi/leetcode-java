# Hot100 第 51 题 · 力扣 200 · 岛屿数量 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0200-number-of-islands/

Hot100 · 图论 · Number of Islands

## 题意

网格中 1 表示陆地、0 表示水。上下左右相连的陆地属于同一岛屿，求岛屿数量。

官方原题：https://leetcode.cn/problems/number-of-islands/

## 课堂示例

输入：grid=["11000","11000","00100","00011"]

预期结果：3

实际结果：3

测试来源：飞书 Hot100 原有课堂示例

## 解法

四方向递归 DFS

## 状态含义

进入 `dfs` 时先验证下标和陆地状态。通过检查就先写 '0'，之后相邻调用即使走回来也会立即返回。

## 状态变化

扫描到一块仍为 '1' 的陆地，岛屿数加一，再用递归 DFS 标记整个岛。

## Java 解法

```java
import java.util.*;

class Solution {
    public int numIslands(char[][] grid) {
        int result = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] == '1') {
                    result++;
                    dfs(grid, row, col);
                }
            }
        }
        return result;
    }

    private void dfs(char[][] grid, int row, int col) {
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length
                || grid[row][col] != '1') {
            return;
        }
        grid[row][col] = '0';
        dfs(grid, row - 1, col);
        dfs(grid, row + 1, col);
        dfs(grid, row, col - 1);
        dfs(grid, row, col + 1);
    }
}
```

## 来源与版本

算法流程来自代码随想录的 Java 解法，保留飞书原有课堂示例。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/graph/LC0200NumberOfIslands.java

源码指纹：dcbf8672c78c0dad154f88732986b81ef96c8be99f3e4cd1806fb3ed7fcd057e

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcnknLTiSSgK5BEKDZIyfkO9c（外部文档可能需要登录，本文仅包含本页已发布的内容。）

代码随想录 Java 解法：https://programmercarl.com/hot100/0200.number-of-islands.html#java

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：218 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0200-number-of-islands/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
