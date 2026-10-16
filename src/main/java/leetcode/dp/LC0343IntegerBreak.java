package leetcode.dp;

import java.util.*;

public class LC0343IntegerBreak {

    public static void main(String[] args) {
        // 测试来源：动态规划笔记中的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe
        int n = 5;
        int expected = 6;
        int actual = new Solution().integerBreak(n);
        System.out.println("Input:\n" + "n = 5");
        System.out.println("Expected:\n" + expected);
        System.out.println("Actual:\n" + actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) throw new AssertionError("结果与预期不同");
    }

    static class Solution {
        public int integerBreak(int n) {
            int[] dp = new int[n + 1];
            for (int i = 2; i <= n; i++) {
                for (int j = 1; j < i; j++) {
                    int product = Math.max(j * (i - j), j * dp[i - j]);
                    dp[i] = Math.max(dp[i], product);
                }
            }
            return dp[n];
        }
    }
}
