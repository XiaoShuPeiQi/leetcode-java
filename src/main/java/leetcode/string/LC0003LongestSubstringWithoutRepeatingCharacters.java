package leetcode.string;

import java.util.*;

public class LC0003LongestSubstringWithoutRepeatingCharacters {
    public static void main(String[] args) {
        // 测试来源：Hot100笔记中相同的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf
        String s = "abcabcbb";
        Object actual = new Solution().lengthOfLongestSubstring(s);
        String expected = "3";
        String result = canonical(actual);
        System.out.println("Input: " + "s=\"abcabcbb\"");
        System.out.println("Expected:");
        System.out.println(expected);
        System.out.println("Actual:");
        System.out.println(result);
        if (!expected.equals(result)) {
            throw new AssertionError("Result: FAIL");
        }
        System.out.println("Result: PASS");
    }


    private static String canonical(Object value) {
        if (value instanceof int[]) {
            return Arrays.toString((int[]) value).replace(" ", "");
        }
        if (value instanceof int[][]) {
            return Arrays.deepToString((int[][]) value).replace(" ", "");
        }
        if (value instanceof List<?>) {
            List<String> parts = new ArrayList<>();
            for (Object item : (List<?>) value) {
                parts.add(canonical(item));
            }

            return "[" + String.join(",", parts) + "]";
        }
        if (value instanceof String) {
            return "\"" + value + "\"";
        }
        return String.valueOf(value);
    }


    static class Solution {
        public int lengthOfLongestSubstring(String s) {
            Set<Character> set = new HashSet<>();
            int left = 0;
            int result = 0;
            for (int right = 0; right < s.length(); right++) {
                char c = s.charAt(right);
                while (set.contains(c)) { // 一直缩到旧的同字符离开窗口
                    set.remove(s.charAt(left));
                    left++;
                }
                set.add(c);
                result = Math.max(result, right - left + 1);
            }
            return result;
        }
    }
}
