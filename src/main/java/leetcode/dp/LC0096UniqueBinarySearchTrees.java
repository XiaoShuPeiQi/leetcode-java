package leetcode.dp;

import java.util.*;

public class LC0096UniqueBinarySearchTrees {

    public static void main(String[] args) {
        // 测试来源：动态规划笔记中的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe
        int n = 3;
        int expected = 5;
        int actual = new Solution().numTrees(n);
        System.out.println("Input:\n" + "n = 3");
        System.out.println("Expected:\n" + expected);
        System.out.println("Actual:\n" + actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) throw new AssertionError("结果与预期不同");
    }

    static class Solution {
        public int numTrees(int n) {
            int[] dp = new int[n + 1];
            dp[0] = 1;
            for (int i = 1; i <= n; i++) {
                for (int root = 1; root <= i; root++) {
                    dp[i] += dp[root - 1] * dp[i - root];
                }
            }
            return dp[n];
        }
    }
}
