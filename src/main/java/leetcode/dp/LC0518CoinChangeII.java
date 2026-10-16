package leetcode.dp;

import java.util.*;

public class LC0518CoinChangeII {

    public static void main(String[] args) {
        // 测试来源：动态规划笔记中的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe
        int amount = 5;
        int[] coins = new int[]{1,2,5};
        int expected = 4;
        int actual = new Solution().change(amount, coins);
        System.out.println("Input:\n" + "amount = 5, coins = [1, 2, 5]");
        System.out.println("Expected:\n" + expected);
        System.out.println("Actual:\n" + actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) throw new AssertionError("结果与预期不同");
    }

    static class Solution {
        public int change(int amount, int[] coins) {
            int[] dp = new int[amount + 1];
            dp[0] = 1;
            for (int coin : coins) {
                for (int j = coin; j <= amount; j++) {
                    dp[j] += dp[j - coin]; // 正序允许重复用当前硬币
                }
            }
            return dp[amount];
        }
    }
}
