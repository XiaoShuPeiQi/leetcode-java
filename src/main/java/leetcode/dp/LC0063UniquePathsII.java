package leetcode.dp;

import java.util.*;

public class LC0063UniquePathsII {

    public static void main(String[] args) {
        // 测试来源：动态规划笔记中的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe
        int[][] obstacleGrid = new int[][]{{0,0,0},{0,1,0},{0,0,0}};
        int expected = 2;
        int actual = new Solution().uniquePathsWithObstacles(obstacleGrid);
        System.out.println("Input:\n" + "obstacleGrid = [[0, 0, 0], [0, 1, 0], [0, 0, 0]]");
        System.out.println("Expected:\n" + expected);
        System.out.println("Actual:\n" + actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) throw new AssertionError("结果与预期不同");
    }

    static class Solution {
        public int uniquePathsWithObstacles(int[][] obstacleGrid) {
            int m = obstacleGrid.length, n = obstacleGrid[0].length;
            if (obstacleGrid[0][0] == 1) return 0;
            int[][] dp = new int[m][n];
            dp[0][0] = 1;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (obstacleGrid[i][j] == 1 || (i == 0 && j == 0)) continue;
                    if (i > 0) dp[i][j] += dp[i - 1][j];
                    if (j > 0) dp[i][j] += dp[i][j - 1];
                }
            }
            return dp[m - 1][n - 1];
        }
    }
}
