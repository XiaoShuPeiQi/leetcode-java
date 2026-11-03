package leetcode.hashtable;

import java.util.*;

public class LC0128LongestConsecutiveSequence {
    public static void main(String[] args) {
        // 测试来源：Hot100笔记中相同的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf
        int[] nums = new int[]{100,4,200,1,3,2};
        Object actual = new Solution().longestConsecutive(nums);
        String expected = "4";
        String result = canonical(actual);
        System.out.println("Input: " + "nums=[100,4,200,1,3,2]");
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
        public int longestConsecutive(int[] nums) {
            int res = 0; // 保存已经找到的最长连续段长度
            Set<Integer> set = new HashSet<>();
            for (int num : nums) {
                set.add(num);
            }

            for (int num : set) {
                if (set.contains(num - 1)) { // 有前驱就不是起点，避免重复扫描同一段
                    continue;
                }

                int current = num;
                int length = 1;
                while (set.contains(current + 1)) {
                    current++;
                    length++;
                }
                if (length > res) {
                    res = length;
                }
            }
            return res;
        }
    }

    // 保留此前版本，便于对照；main和Hot100调试器使用上方的清晰写法。
    static class SolutionOriginal {
        public int longestConsecutive(int[] nums) {
            Set<Integer> set = new HashSet<>();
            for (int num : nums) {
                set.add(num);
            }
            int result = 0;
            for (int num : set) {
                if (set.contains(num - 1)) { // 只从每段的起点开始，避免重复扫描
                    continue;
                }
                int current = num;
                int length = 1;
                while (set.contains(current + 1)) {
                    current++;
                    length++;
                }
                result = Math.max(result, length);
            }
            return result;
        }
    }
}
