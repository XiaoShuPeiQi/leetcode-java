package leetcode.graph;

import java.util.*;
import common.TreeSupport;


public class LC0994RottingOranges {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "grid=[[2,1,1],[1,1,0],[0,1,1]]",
                "4", String.valueOf(runSolution()));
    }

    // 测试来源：力扣示例与 Hot100 笔记。
    static Object runSolution() {
        int[][] grid = {{2,1,1},{1,1,0},{0,1,1}};
        return new Solution().orangesRotting(grid);
    }

    static class Solution {
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
}
