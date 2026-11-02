package leetcode.hashtable;

import java.util.*;

public class LC0242ValidAnagram {

    public static void main(String[] args) {
        // 测试来源：飞书哈希表笔记的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/F4qJw6Pr2if4LkkJBjBc8aM3nXd
        String s = "aab";
        String t = "aba";
        boolean expected = true;
        boolean actual = new Solution().isAnagram(s, t);
        System.out.println("Input: s = \"aab\", t = \"aba\"");
        System.out.println("Expected: " + expected);
        System.out.println("Actual: " + actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) {
            throw new AssertionError("结果与预期不同");
        }
    }

    static class Solution {
        public boolean isAnagram(String s, String t) {
            if (s.length() != t.length()) {
                return false;
            }

            int[] count = new int[26];
            for (int i = 0; i < s.length(); i++) {
                count[s.charAt(i) - 'a']++;
            }
            for (int i = 0; i < t.length(); i++) {
                count[t.charAt(i) - 'a']--;
            }

            // 每个格子都归零，才是每种字母数量相同。
            for (int value : count) {
                if (value != 0) {
                    return false;
                }
            }
            return true;
        }
    }
}
