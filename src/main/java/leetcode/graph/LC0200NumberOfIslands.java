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
            int res = 0;
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[0].length; col++) {
                    if (grid[row][col] == '1') {
                        res++;
                        flood(grid, row, col);
                    }
                }
            }
            return res;
        }

        private void flood(char[][] grid, int row, int col) {
            int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            Queue<int[]> queue = new ArrayDeque<>();
            grid[row][col] = '0';
            queue.offer(new int[]{row, col});
            while (!queue.isEmpty()) {
                int[] cell = queue.poll();
                for (int[] direction : directions) {
                    int nextRow = cell[0] + direction[0];
                    int nextCol = cell[1] + direction[1];
                    if (nextRow < 0 || nextRow >= grid.length
                            || nextCol < 0 || nextCol >= grid[0].length) {
                        continue;
                    }
                    if (grid[nextRow][nextCol] == '1') {
                        grid[nextRow][nextCol] = '0'; // 入队时就标记
                        queue.offer(new int[]{nextRow, nextCol});
                    }
                }
            }
        }
    }
}
