package leetcode.dp;

import java.util.*;

public class LC0072EditDistance {

    public static void main(String[] args) {
        // 测试来源：动态规划笔记中的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe
        String word1 = "horse";
        String word2 = "ros";
        int expected = 3;
        int actual = new Solution().minDistance(word1, word2);
        System.out.println("Input:\n" + "word1 = \"horse\", word2 = \"ros\"");
        System.out.println("Expected:\n" + expected);
        System.out.println("Actual:\n" + actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) throw new AssertionError("结果与预期不同");
    }

    static class Solution {
        public int minDistance(String word1, String word2) {
            int[][] dp = new int[word1.length() + 1][word2.length() + 1];
            for (int i = 0; i <= word1.length(); i++) dp[i][0] = i;
            for (int j = 0; j <= word2.length(); j++) dp[0][j] = j;
            for (int i = 1; i <= word1.length(); i++) {
                for (int j = 1; j <= word2.length(); j++) {
                    if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1];
                    } else {
                        dp[i][j] = Math.min(dp[i - 1][j - 1],
                                Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
                    }
                }
            }
            return dp[word1.length()][word2.length()];
        }
    }
}
