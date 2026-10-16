package leetcode.dp;

import java.util.*;

public class LC0714BestTimetoBuyandSellStockwithTransactionFee {

    public static void main(String[] args) {
        // 测试来源：动态规划笔记中的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe
        int[] prices = new int[]{1,3,2,8,4,9};
        int fee = 2;
        int expected = 8;
        int actual = new Solution().maxProfit(prices, fee);
        System.out.println("Input:\n" + "prices = [1, 3, 2, 8, 4, 9], fee = 2");
        System.out.println("Expected:\n" + expected);
        System.out.println("Actual:\n" + actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) throw new AssertionError("结果与预期不同");
    }

    static class Solution {
        public int maxProfit(int[] prices, int fee) {
            int n = prices.length;
            if (n == 0) return 0;
            int[][] dp = new int[n][2];
            dp[0][0] = -prices[0];
            for (int i = 1; i < n; i++) {
                dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] - prices[i]);
                dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] + prices[i] - fee);
            }
            return dp[n - 1][1];
        }
    }
}
