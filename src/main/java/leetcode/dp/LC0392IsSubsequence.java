package leetcode.dp;

import java.util.*;

public class LC0392IsSubsequence {

    public static void main(String[] args) {
        // 测试来源：动态规划笔记中的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe
        String s = "abc";
        String t = "ahbgdc";
        boolean expected = true;
        boolean actual = new Solution().isSubsequence(s, t);
        System.out.println("Input:\n" + "s = \"abc\", t = \"ahbgdc\"");
        System.out.println("Expected:\n" + expected);
        System.out.println("Actual:\n" + actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) throw new AssertionError("结果与预期不同");
    }

    static class Solution {
        public boolean isSubsequence(String s, String t) {
            int[][] dp = new int[s.length() + 1][t.length() + 1];
            for (int i = 1; i <= s.length(); i++) {
                for (int j = 1; j <= t.length(); j++) {
                    if (s.charAt(i - 1) == t.charAt(j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1] + 1;
                    } else {
                        dp[i][j] = dp[i][j - 1];
                    }
                }
            }
            return dp[s.length()][t.length()] == s.length();
        }
    }
}
