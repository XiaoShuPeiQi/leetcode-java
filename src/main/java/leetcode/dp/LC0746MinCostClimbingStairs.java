package leetcode.dp;

import java.util.*;

public class LC0746MinCostClimbingStairs {

    public static void main(String[] args) {
        // 测试来源：动态规划笔记中的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe
        int[] cost = new int[]{10,15,20};
        int expected = 15;
        int actual = new Solution().minCostClimbingStairs(cost);
        System.out.println("Input:\n" + "cost = [10, 15, 20]");
        System.out.println("Expected:\n" + expected);
        System.out.println("Actual:\n" + actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) throw new AssertionError("结果与预期不同");
    }

    static class Solution {
        public int minCostClimbingStairs(int[] cost) {
            int n = cost.length;
            int[] dp = new int[n + 1];
            for (int i = 2; i <= n; i++) {
                dp[i] = Math.min(dp[i - 1] + cost[i - 1],
                        dp[i - 2] + cost[i - 2]); // 付费发生在离开台阶时
            }
            return dp[n];
        }
    }
}
