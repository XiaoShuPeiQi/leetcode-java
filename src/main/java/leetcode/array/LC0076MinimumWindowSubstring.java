package leetcode.array;

import java.util.*;

public class LC0076MinimumWindowSubstring {
    public static void main(String[] args) {
        // 来源：数组章节本次修订的实例走过程。
        String s = "ADOBECODEBANC";
        String t = "ABC";
        String expected = "BANC";
        String actual = new Solution().minWindow(s, t);
        System.out.println("Input: s = \"ADOBECODEBANC\", t = \"ABC\"");
        System.out.println("Expected:");
        System.out.println(expected);
        System.out.println("Actual:");
        System.out.println(actual);
        boolean passed = actual.equals(expected);
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) {
            throw new AssertionError("结果与预期不同");
        }
    }

    static class Solution {
        public String minWindow(String s, String t) {
            if (t.length() == 0 || s.length() < t.length()) {
                return "";
            }
            int[] need = new int[128];
            for (int i = 0; i < t.length(); i++) {
                need[t.charAt(i)]++;
            }
            int missing = t.length();
            int left = 0;
            int bestStart = 0;
            int bestLength = Integer.MAX_VALUE;
            for (int right = 0; right < s.length(); right++) {
                char added = s.charAt(right);
                if (need[added] > 0) { // 加入前仍欠这个字符，才补掉缺口
                    missing--;
                }
                need[added]--;
                while (missing == 0) {
                    int length = right - left + 1;
                    if (length < bestLength) {
                        bestStart = left;
                        bestLength = length;
                    }
                    char removed = s.charAt(left);
                    need[removed]++;
                    if (need[removed] > 0) { // 移走后变成正数，窗口重新欠字符
                        missing++;
                    }
                    left++;
                }
            }
            return bestLength == Integer.MAX_VALUE
                    ? "" : s.substring(bestStart, bestStart + bestLength);
        }
    }
}
