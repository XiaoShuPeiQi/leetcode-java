package leetcode.dp;

import java.util.*;

public class LC0139WordBreak {

    public static void main(String[] args) {
        // 测试来源：动态规划笔记中的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe
        String s = "leetcode";
        List<String> wordDict = Arrays.asList("leet","code");
        boolean expected = true;
        boolean actual = new Solution().wordBreak(s, wordDict);
        System.out.println("Input:\n" + "s = \"leetcode\", wordDict = [\"leet\", \"code\"]");
        System.out.println("Expected:\n" + expected);
        System.out.println("Actual:\n" + actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) throw new AssertionError("结果与预期不同");
    }

    static class Solution {
        public boolean wordBreak(String s, List<String> wordDict) {
            boolean[] dp = new boolean[s.length() + 1];
            dp[0] = true;
            for (int i = 1; i <= s.length(); i++) {
                for (String word : wordDict) {
                    int len = word.length();
                    if (i >= len && dp[i - len] && s.startsWith(word, i - len)) {
                        dp[i] = true;
                        break;
                    }
                }
            }
            return dp[s.length()];
        }
    }
}
