# 37 · 解数独 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0037-sudoku-solver/

回溯 · Sudoku Solver

## 题意

填满一个 9×9 数独棋盘的空格，使每行、每列及每个 3×3 宫都恰好包含数字 1 到 9。直接修改输入，题目保证解唯一。

官方原题：https://leetcode.cn/problems/sudoku-solver/

## 课堂示例

输入：board = LeetCode 官方示例 1（见下方棋盘）

预期结果：[[5, 3, 4, 6, 7, 8, 9, 1, 2], [6, 7, 2, 1, 9, 5, 3, 4, 8], [1, 9, 8, 3, 4, 2, 5, 6, 7], [8, 5, 9, 7, 6, 1, 4, 2, 3], [4, 2, 6, 8, 5, 3, 7, 9, 1], [7, 1, 3, 9, 2, 4, 8, 5, 6], [9, 6, 1, 5, 3, 7, 2, 8, 4], [2, 8, 7, 4, 1, 9, 6, 3, 5], [3, 4, 5, 2, 8, 6, 1, 7, 9]]

实际结果：[[5, 3, 4, 6, 7, 8, 9, 1, 2], [6, 7, 2, 1, 9, 5, 3, 4, 8], [1, 9, 8, 3, 4, 2, 5, 6, 7], [8, 5, 9, 7, 6, 1, 4, 2, 3], [4, 2, 6, 8, 5, 3, 7, 9, 1], [7, 1, 3, 9, 2, 4, 8, 5, 6], [9, 6, 1, 5, 3, 7, 2, 8, 4], [2, 8, 7, 4, 1, 9, 6, 3, 5], [3, 4, 5, 2, 8, 6, 1, 7, 9]]

测试来源：LeetCode 37 官方示例 1；飞书只给出抽象 A/B 空格过程，没有具体棋盘。

## Java 解法

```java
import java.util.*;

class Solution {
    public void solveSudoku(char[][] board) {
        boolean[][] rows = new boolean[9][10];
        boolean[][] columns = new boolean[9][10];
        boolean[][] boxes = new boolean[9][10];
        List<int[]> spaces = new ArrayList<>();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == '.') {
                    spaces.add(new int[] {row, col});
                } else {
                    int digit = board[row][col] - '0';
                    int box = (row / 3) * 3 + col / 3;
                    rows[row][digit] = true;
                    columns[col][digit] = true;
                    boxes[box][digit] = true;
                }
            }
        }
        backtracking(board, spaces, 0, rows, columns, boxes);
    }

    private boolean backtracking(char[][] board, List<int[]> spaces, int index,
            boolean[][] rows, boolean[][] columns, boolean[][] boxes) {
        if (index == spaces.size()) {
            return true;
        }
        int row = spaces.get(index)[0];
        int col = spaces.get(index)[1];
        // 宫编号：0～8。
        int box = (row / 3) * 3 + col / 3;
        for (int digit = 1; digit <= 9; digit++) {
            if (rows[row][digit] || columns[col][digit] || boxes[box][digit]) {
                continue;
            }
            board[row][col] = (char) ('0' + digit);
            rows[row][digit] = true;
            columns[col][digit] = true;
            boxes[box][digit] = true;
            // 找到解就返回，保留棋盘。
            if (backtracking(board, spaces, index + 1, rows, columns, boxes)) {
                return true;
            }
            // 失败后恢复格子和占用标记。
            board[row][col] = '.';
            rows[row][digit] = false;
            columns[col][digit] = false;
            boxes[box][digit] = false;
        }
        return false;
    }
}
```

## 来源与版本

最新飞书算法与本地导入版本一致；注释可能不同。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/backtracking/LC0037SudokuSolver.java

源码指纹：962b3e5847431de151189ee1477ac917511674a2ab2b3f8bf870790962edc59f

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/VSYBdzUkuoIxf3xuSfVcsgKkn3g（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：148380 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0037-sudoku-solver/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
