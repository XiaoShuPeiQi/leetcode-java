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
            int[] res = new int[nums.length]; // res[i]最终保存除nums[i]外的全部元素乘积
            int leftProduct = 1;
            for (int i = 0; i < nums.length; i++) {
                res[i] = leftProduct; // 先写入左侧乘积，此时不包含nums[i]
                leftProduct *= nums[i];
            }

            int rightProduct = 1;
            for (int i = nums.length - 1; i >= 0; i--) {
                res[i] = res[i] * rightProduct; // 左侧乘积再乘右侧乘积
                rightProduct *= nums[i];
            }
            return res;
        }
    }

    // 保留此前版本，便于对照；main和Hot100调试器使用上方的清晰写法。
    static class SolutionOriginal {
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
