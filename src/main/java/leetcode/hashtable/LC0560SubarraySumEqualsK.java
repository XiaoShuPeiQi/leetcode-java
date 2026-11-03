package leetcode.hashtable;

import java.util.*;

public class LC0560SubarraySumEqualsK {
    public static void main(String[] args) {
        // 测试来源：Hot100笔记中相同的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf
        int[] nums = new int[]{1,1,1};
        int k = 2;
        Object actual = new Solution().subarraySum(nums, k);
        String expected = "2";
        String result = canonical(actual);
        System.out.println("Input: " + "nums=[1,1,1]; k=2");
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
        public int subarraySum(int[] nums, int k) {
            Map<Integer, Integer> count = new HashMap<>();
            count.put(0, 1); // 空前缀，让从下标0开始的区间也能被统计
            int sum = 0;
            int result = 0;
            for (int num : nums) {
                sum += num;
                result += count.getOrDefault(sum - k, 0); // 先查之前的前缀，避免把空区间计入
                count.put(sum, count.getOrDefault(sum, 0) + 1);
            }
            return result;
        }
    }
}
