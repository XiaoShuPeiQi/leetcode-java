package leetcode.backtracking;

import java.util.*;

public class LC0051NQueens {

    public static void main(String[] args) {
        // 测试用例：飞书回溯笔记 3.14 的执行过程示例
        // 来源：https://acn581cm6pdd.feishu.cn/docx/VSYBdzUkuoIxf3xuSfVcsgKkn3g
        int n = 4;
        List<List<String>> expected = List.of(
                List.of(".Q..", "...Q", "Q...", "..Q."),
                List.of("..Q.", "Q...", "...Q", ".Q.."));

        System.out.println("LeetCode 51 - N 皇后");
        System.out.println("Input:");
        System.out.println("n = " + n);

        Solution solution = new Solution();
        List<List<String>> actual = solution.solveNQueens(n);

        System.out.println("Expected:");
        System.out.println(expected);
        System.out.println("Actual:");
        System.out.println(actual);
        // 仅忽略结果外部顺序，保留序列、分段或棋盘内部顺序及重复次数。
        boolean passed = resultCounts(actual).equals(resultCounts(expected));
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) {
            throw new AssertionError("N 皇后：实际结果与预期结果不一致");
        }
    }

    private static <T> Map<T, Integer> resultCounts(List<T> results) {
        Map<T, Integer> counts = new HashMap<>();
        for (T result : results) {
            counts.merge(result, 1, Integer::sum);
        }
        return counts;
    }

    // 飞书原始代码：只增加 static 包装与一层缩进，算法保持原样。
    static class Solution {
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
}
