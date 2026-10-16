package leetcode.dp;

import java.util.*;

public class LC0123BestTimetoBuyandSellStockIII {

    public static void main(String[] args) {
        // 测试来源：动态规划笔记中的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe
        int[] prices = new int[]{3,3,5,0,0,3,1,4};
        int expected = 6;
        int actual = new Solution().maxProfit(prices);
        System.out.println("Input:\n" + "prices = [3, 3, 5, 0, 0, 3, 1, 4]");
        System.out.println("Expected:\n" + expected);
        System.out.println("Actual:\n" + actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) throw new AssertionError("结果与预期不同");
    }

    static class Solution {
        public int maxProfit(int[] prices) {
            int n = prices.length;
            if (n == 0) return 0;
            int[][] dp = new int[n][5];
            dp[0][1] = dp[0][3] = -prices[0];
            for (int i = 1; i < n; i++) {
                for (int j = 1; j <= 4; j++) {
                    int delta = j % 2 == 1 ? -prices[i] : prices[i];
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - 1] + delta);
                }
            }
            return dp[n - 1][4];
        }
    }
}
