package leetcode.dp;

import common.TreeSupport;

public class LC0064MinimumPathSum {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "grid = [[1, 3, 1], [1, 5, 1], [4, 2, 1]]",
                7, runSolution());
    }

    // 力扣官方示例，与本节推演一致。
    static Object runSolution() {
        int[][] grid = {{1, 3, 1}, {1, 5, 1}, {4, 2, 1}};
        return new Solution().minPathSum(grid);
    }

    static class Solution {
        public int minPathSum(int[][] grid) {
            int m = grid.length;
            int n = grid[0].length;
            for (int j = 1; j < n; j++) {
                grid[0][j] += grid[0][j - 1];
            }
            for (int i = 1; i < m; i++) {
                grid[i][0] += grid[i - 1][0];
            }
            for (int i = 1; i < m; i++) {
                for (int j = 1; j < n; j++) {
                    grid[i][j] += Math.min(grid[i - 1][j], grid[i][j - 1]);
                }
            }
            return grid[m - 1][n - 1];
        }
    }
}
