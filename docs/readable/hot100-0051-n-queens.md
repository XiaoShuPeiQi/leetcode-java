# Hot100 第 62 题 · 力扣 51 · N 皇后 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0051-n-queens/

Hot100 · 回溯 · N Queens

## 题意

在 n×n 棋盘放 n 个皇后，任意两个不能同列、同行或同对角线。返回所有可行棋盘，用 Q 标记皇后，用 . 标记空格。

官方原题：https://leetcode.cn/problems/n-queens/

## 课堂示例

输入：n = 4

预期结果：[[.Q.., ...Q, Q..., ..Q.], [..Q., Q..., ...Q, .Q..]]

实际结果：[[.Q.., ...Q, Q..., ..Q.], [..Q., Q..., ...Q, .Q..]]

测试来源：飞书 Hot100 第 62 题「实例走过程」

## 状态含义

进入 backtracking(row, ...) 时，前 row 行各有一个皇后，布尔标记与棋盘对应。放置后递归到 row + 1。当 row == n 时逐行转成字符串保存到 result。试下一候选列前，恢复本次写入的棋盘格和三个标记。

## 状态变化

每层为一行选一列，同时检查列和两条对角线，递归回来时一起撤销。

## Java 解法

```java
import java.util.*;

class Solution {
    public List<List<String>> solveNQueens(int n) {
        char[][] board = new char[n][n];
        for (char[] row : board) {
            Arrays.fill(row, '.');
        }
        List<List<String>> result = new ArrayList<>();
        boolean[] columns = new boolean[n];
        boolean[] diagonals = new boolean[2 * n - 1];
        boolean[] antiDiagonals = new boolean[2 * n - 1];
        backtracking(0, board, columns, diagonals, antiDiagonals, result);
        return result;
    }

    private void backtracking(int row, char[][] board, boolean[] columns,
            boolean[] diagonals, boolean[] antiDiagonals, List<List<String>> result) {
        int n = board.length;
        if (row == n) {
            List<String> path = new ArrayList<>();
            for (char[] line : board) {
                path.add(new String(line));
            }
            result.add(path);
            return;
        }
        for (int col = 0; col < n; col++) {
            // 同一斜线 row - col 相同。
            int diagonal = row - col + n - 1;
            // 另一方向 row + col 相同。
            int antiDiagonal = row + col;
            if (columns[col] || diagonals[diagonal] || antiDiagonals[antiDiagonal]) {
                continue;
            }
            board[row][col] = 'Q';
            columns[col] = true;
            diagonals[diagonal] = true;
            antiDiagonals[antiDiagonal] = true;
            backtracking(row + 1, board, columns, diagonals, antiDiagonals, result);
            // 撤销皇后和占用标记。
            board[row][col] = '.';
            columns[col] = false;
            diagonals[diagonal] = false;
            antiDiagonals[antiDiagonal] = false;
        }
    }
}
```

## 来源与版本

算法学习总笔记（Java）原码。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/backtracking/LC0051NQueens.java

源码指纹：36de0198e176330934904923b2a68a65a80e1fa3473e6be632005d85e21fa052

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcnxgBFb6U9jHVkZ68qxWILTb（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：531 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0051-n-queens/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
