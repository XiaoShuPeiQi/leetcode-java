package leetcode.string;

import java.util.*;

public class LC0438FindAllAnagramsInAString {
    public static void main(String[] args) {
        // 测试来源：Hot100笔记中相同的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf
        String s = "cbaebabacd";
        String p = "abc";
        Object actual = new Solution().findAnagrams(s, p);
        String expected = "[0,6]";
        String result = canonical(actual);
        System.out.println("Input: " + "s=\"cbaebabacd\"; p=\"abc\"");
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
        public List<Integer> findAnagrams(String s, String p) {
            List<Integer> res = new ArrayList<>(); // 保存每个匹配窗口的起始下标
            int[] need = new int[26];
            int[] window = new int[26];
            for (int i = 0; i < p.length(); i++) {
                need[p.charAt(i) - 'a']++;
            }
            int left = 0;
            for (int right = 0; right < s.length(); right++) {
                window[s.charAt(right) - 'a']++;
                if (right - left + 1 > p.length()) { // 固定窗口只移走一个最左字符
                    window[s.charAt(left) - 'a']--;
                    left++;
                }
                if (right - left + 1 == p.length() && Arrays.equals(need, window)) {
                    res.add(left); // 当前窗口的左端就是一个答案下标
                }
            }
            return res;
        }
    }

    // 保留此前版本，便于对照；main和Hot100调试器使用上方的清晰写法。
    static class SolutionOriginal {
        public List<Integer> findAnagrams(String s, String p) {
            List<Integer> result = new ArrayList<>();
            int[] need = new int[26];
            int[] window = new int[26];
            for (int i = 0; i < p.length(); i++) {
                need[p.charAt(i) - 'a']++;
            }
            int left = 0;
            for (int right = 0; right < s.length(); right++) {
                window[s.charAt(right) - 'a']++;
                if (right - left + 1 > p.length()) { // 固定窗口只移走一个最左字符
                    window[s.charAt(left) - 'a']--;
                    left++;
                }
                if (right - left + 1 == p.length() && Arrays.equals(need, window)) {
                    result.add(left);
                }
            }
            return result;
        }
    }
}
