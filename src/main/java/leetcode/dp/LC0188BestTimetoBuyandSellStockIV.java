package leetcode.dp;

import java.util.*;

public class LC0188BestTimetoBuyandSellStockIV {

    public static void main(String[] args) {
        // 测试来源：动态规划笔记中的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe
        int k = 2;
        int[] prices = new int[]{3,2,6,5,0,3};
        int expected = 7;
        int actual = new Solution().maxProfit(k, prices);
        System.out.println("Input:\n" + "k = 2, prices = [3, 2, 6, 5, 0, 3]");
        System.out.println("Expected:\n" + expected);
        System.out.println("Actual:\n" + actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) throw new AssertionError("结果与预期不同");
    }

    static class Solution {
        public int maxProfit(int k, int[] prices) {
            int n = prices.length;
            if (k == 0 || n < 2) return 0;
            k = Math.min(k, n / 2);
            int[][] dp = new int[n][2 * k + 1];
            for (int j = 1; j <= 2 * k; j += 2) dp[0][j] = -prices[0];
            for (int i = 1; i < n; i++) {
                for (int j = 1; j <= 2 * k; j++) {
                    int delta = j % 2 == 1 ? -prices[i] : prices[i];
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - 1] + delta);
                }
            }
            return dp[n - 1][2 * k];
        }
    }
}
