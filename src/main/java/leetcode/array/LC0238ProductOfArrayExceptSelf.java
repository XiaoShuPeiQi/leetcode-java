package leetcode.array;

import java.util.*;

public class LC0238ProductOfArrayExceptSelf {
    public static void main(String[] args) {
        // 测试来源：Hot100笔记中相同的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf
        int[] nums = new int[]{1,2,3,4};
        Object actual = new Solution().productExceptSelf(nums);
        String expected = "[24,12,8,6]";
        String result = canonical(actual);
        System.out.println("Input: " + "nums=[1,2,3,4]");
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
        public int[] productExceptSelf(int[] nums) {
            int[] result = new int[nums.length];
            int left = 1;
            for (int i = 0; i < nums.length; i++) {
                result[i] = left; // 先存左侧乘积，再纳入当前数
                left *= nums[i];
            }
            int right = 1;
            for (int i = nums.length - 1; i >= 0; i--) {
                result[i] *= right;
                right *= nums[i];
            }
            return result;
        }
    }
}
