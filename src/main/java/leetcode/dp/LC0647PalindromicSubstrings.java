package leetcode.dp;

import java.util.*;

public class LC0647PalindromicSubstrings {

    public static void main(String[] args) {
        // 测试来源：动态规划笔记中的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe
        String s = "aaa";
        int expected = 6;
        int actual = new Solution().countSubstrings(s);
        System.out.println("Input:\n" + "s = \"aaa\"");
        System.out.println("Expected:\n" + expected);
        System.out.println("Actual:\n" + actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) throw new AssertionError("结果与预期不同");
    }

    static class Solution {
        public int countSubstrings(String s) {
            boolean[][] dp = new boolean[s.length()][s.length()];
            int result = 0;
            for (int i = s.length() - 1; i >= 0; i--) {
                for (int j = i; j < s.length(); j++) {
                    if (s.charAt(i) == s.charAt(j) && (j - i <= 1 || dp[i + 1][j - 1])) {
                        dp[i][j] = true;
                        result++;
                    }
                }
            }
            return result;
        }
    }
}
