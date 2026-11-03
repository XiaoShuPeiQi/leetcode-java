package leetcode.array;

import java.util.*;

public class LC0041FirstMissingPositive {
    public static void main(String[] args) {
        // 测试来源：Hot100笔记中相同的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf
        int[] nums = new int[]{3,4,-1,1};
        Object actual = new Solution().firstMissingPositive(nums);
        String expected = "2";
        String result = canonical(actual);
        System.out.println("Input: " + "nums=[3,4,-1,1]");
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
        public int firstMissingPositive(int[] nums) {
            int n = nums.length;
            for (int i = 0; i < n; i++) {
                while (nums[i] >= 1 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                    int target = nums[i] - 1; // 重复值已就位时不再交换，避免死循环
                    int temp = nums[target];
                    nums[target] = nums[i];
                    nums[i] = temp;
                }
            }
            for (int i = 0; i < n; i++) {
                if (nums[i] != i + 1) {
                    return i + 1;
                }
            }
            return n + 1;
        }
    }
}
