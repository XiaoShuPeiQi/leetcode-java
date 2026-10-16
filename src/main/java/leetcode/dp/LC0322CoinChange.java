package leetcode.dp;

import java.util.*;

public class LC0322CoinChange {

    public static void main(String[] args) {
        // 测试来源：动态规划笔记中的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe
        int[] coins = new int[]{1,2,5};
        int amount = 11;
        int expected = 3;
        int actual = new Solution().coinChange(coins, amount);
        System.out.println("Input:\n" + "coins = [1, 2, 5], amount = 11");
        System.out.println("Expected:\n" + expected);
        System.out.println("Actual:\n" + actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) throw new AssertionError("结果与预期不同");
    }

    static class Solution {
        public int coinChange(int[] coins, int amount) {
            int[] dp = new int[amount + 1];
            for (int i = 1; i <= amount; i++) dp[i] = amount + 1;
            for (int i = 1; i <= amount; i++) {
                for (int coin : coins) {
                    if (coin <= i) dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
            return dp[amount] > amount ? -1 : dp[amount];
        }
    }
}
