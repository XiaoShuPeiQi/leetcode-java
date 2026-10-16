package leetcode.dp;

import java.util.*;

public class LC0115DistinctSubsequences {

    public static void main(String[] args) {
        // 测试来源：动态规划笔记中的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe
        String s = "rabbbit";
        String t = "rabbit";
        int expected = 3;
        int actual = new Solution().numDistinct(s, t);
        System.out.println("Input:\n" + "s = \"rabbbit\", t = \"rabbit\"");
        System.out.println("Expected:\n" + expected);
        System.out.println("Actual:\n" + actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) throw new AssertionError("结果与预期不同");
    }

    static class Solution {
        public int numDistinct(String s, String t) {
            long[][] dp = new long[s.length() + 1][t.length() + 1];
            for (int i = 0; i <= s.length(); i++) dp[i][0] = 1;
            for (int i = 1; i <= s.length(); i++) {
                for (int j = 1; j <= t.length(); j++) {
                    dp[i][j] = dp[i - 1][j]; // 不使用当前 s 字符
                    if (s.charAt(i - 1) == t.charAt(j - 1)) {
                        dp[i][j] = Math.min(Integer.MAX_VALUE, dp[i][j] + dp[i - 1][j - 1]);
                    }
                }
            }
            return (int) dp[s.length()][t.length()];
        }
    }
}
