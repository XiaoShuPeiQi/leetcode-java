package leetcode.dp;

import common.TreeSupport;

public class LC0005LongestPalindromicSubstring {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "s = \"cbbd\"",
                "bb", runSolution());
    }

    // 力扣官方示例，与本节推演一致。
    static Object runSolution() {
        String s = "cbbd";
        return new Solution().longestPalindrome(s);
    }

    static class Solution {
        public String longestPalindrome(String s) {
            int start = 0;
            int bestLength = 0;
            for (int i = 0; i < s.length(); i++) {
                int length = Math.max(expand(s, i, i), expand(s, i, i + 1));
                if (length > bestLength) {
                    start = i - (length - 1) / 2;
                    bestLength = length;
                }
            }
            return s.substring(start, start + bestLength);
        }

        private int expand(String s, int left, int right) {
            while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
                left--;
                right++;
            }
            return right - left - 1;
        }
    }
}
