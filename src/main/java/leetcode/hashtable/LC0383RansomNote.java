package leetcode.hashtable;

import java.util.*;

public class LC0383RansomNote {

    public static void main(String[] args) {
        // 测试来源：飞书哈希表笔记的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/F4qJw6Pr2if4LkkJBjBc8aM3nXd
        String ransomNote = "aa";
        String magazine = "aab";
        boolean expected = true;
        boolean actual = new Solution().canConstruct(ransomNote, magazine);
        System.out.println("Input: ransomNote = \"aa\", magazine = \"aab\"");
        System.out.println("Expected: " + expected);
        System.out.println("Actual: " + actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) {
            throw new AssertionError("结果与预期不同");
        }
    }

    static class Solution {
        public boolean canConstruct(String ransomNote, String magazine) {
            if (ransomNote.length() > magazine.length()) {
                return false;
            }

            int[] count = new int[26];
            for (int i = 0; i < magazine.length(); i++) {
                count[magazine.charAt(i) - 'a']++;
            }
            for (int i = 0; i < ransomNote.length(); i++) {
                int index = ransomNote.charAt(i) - 'a';
                count[index]--;
                // 可以有剩余，但不能出现欠缺。
                if (count[index] < 0) {
                    return false;
                }
            }
            return true;
        }
    }
}
