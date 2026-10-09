package leetcode.backtracking;

import java.util.*;

public class LC0037SudokuSolver {

    public static void main(String[] args) {
        // 测试用例：LeetCode 37 官方示例 1；飞书只给出抽象 A/B 空格过程，没有具体棋盘。
        // 来源：https://acn581cm6pdd.feishu.cn/docx/VSYBdzUkuoIxf3xuSfVcsgKkn3g
        char[][] board = {
            "53..7....".toCharArray(),
            "6..195...".toCharArray(),
            ".98....6.".toCharArray(),
            "8...6...3".toCharArray(),
            "4..8.3..1".toCharArray(),
            "7...2...6".toCharArray(),
            ".6....28.".toCharArray(),
            "...419..5".toCharArray(),
            "....8..79".toCharArray()
        };
        char[][] expected = {
            "534678912".toCharArray(),
            "672195348".toCharArray(),
            "198342567".toCharArray(),
            "859761423".toCharArray(),
            "426853791".toCharArray(),
            "713924856".toCharArray(),
            "961537284".toCharArray(),
            "287419635".toCharArray(),
            "345286179".toCharArray()
        };

        System.out.println("LeetCode 37 - 解数独");
        System.out.println("Input:");
        System.out.println("board = LeetCode 官方示例 1");
        System.out.println(Arrays.deepToString(board));

        Solution solution = new Solution();
        solution.solveSudoku(board);
        char[][] actual = board;

        System.out.println("Expected:");
        System.out.println(Arrays.deepToString(expected));
        System.out.println("Actual:");
        System.out.println(Arrays.deepToString(actual));
        // 数独原地修改棋盘，按每一行的字符内容比较。
        boolean passed = Arrays.deepEquals(actual, expected);
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) {
            throw new AssertionError("解数独：实际结果与预期结果不一致");
        }
    }



    // 飞书原始代码：只增加 static 包装与一层缩进，算法保持原样。
    static class Solution {
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
}
