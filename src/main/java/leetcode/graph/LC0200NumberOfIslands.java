package leetcode.graph;

import java.util.*;
import common.TreeSupport;


public class LC0200NumberOfIslands {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "grid=[\"11000\",\"11000\",\"00100\",\"00011\"]",
                "3", String.valueOf(runSolution()));
    }

    // 测试来源：力扣示例与 Hot100 笔记。
    static Object runSolution() {
        char[][] grid = {"11000".toCharArray(), "11000".toCharArray(),
                "00100".toCharArray(), "00011".toCharArray()};
        return new Solution().numIslands(grid);
    }

    static class Solution {
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
}
