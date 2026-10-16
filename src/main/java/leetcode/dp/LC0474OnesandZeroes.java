package leetcode.dp;

import java.util.*;

public class LC0474OnesandZeroes {

    public static void main(String[] args) {
        // 测试来源：动态规划笔记中的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe
        String[] strs = new String[]{"10","0001","111001","1","0"};
        int m = 5;
        int n = 3;
        int expected = 4;
        int actual = new Solution().findMaxForm(strs, m, n);
        System.out.println("Input:\n" + "strs = [\"10\", \"0001\", \"111001\", \"1\", \"0\"], m = 5, n = 3");
        System.out.println("Expected:\n" + expected);
        System.out.println("Actual:\n" + actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) throw new AssertionError("结果与预期不同");
    }

    static class Solution {
        public int findMaxForm(String[] strs, int m, int n) {
            int[][] dp = new int[m + 1][n + 1];
            for (String str : strs) {
                int zeros = 0, ones = 0;
                for (char c : str.toCharArray()) {
                    if (c == '0') zeros++;
                    else ones++;
                }
                for (int i = m; i >= zeros; i--) {
                    for (int j = n; j >= ones; j--) {
                        dp[i][j] = Math.max(dp[i][j], dp[i - zeros][j - ones] + 1);
                    }
                }
            }
            return dp[m][n];
        }
    }
}
