package leetcode.dp;

import java.util.*;

public class LC0509FibonacciNumber {

    public static void main(String[] args) {
        // 测试来源：动态规划笔记中的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe
        int n = 5;
        int expected = 5;
        int actual = new Solution().fib(n);
        System.out.println("Input:\n" + "n = 5");
        System.out.println("Expected:\n" + expected);
        System.out.println("Actual:\n" + actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) throw new AssertionError("结果与预期不同");
    }

    static class Solution {
        public int fib(int n) {
            if (n < 2) return n;
            int[] dp = new int[n + 1];
            dp[1] = 1;
            for (int i = 2; i <= n; i++) {
                dp[i] = dp[i - 1] + dp[i - 2]; // 两个更小状态合成当前状态
            }
            return dp[n];
        }
    }
}
