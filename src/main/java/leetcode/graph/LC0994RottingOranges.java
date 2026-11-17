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
}
