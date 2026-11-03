package leetcode.array;

import java.util.*;

public class LC0189RotateArray {
    public static void main(String[] args) {
        // 测试来源：Hot100笔记中相同的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf
        int[] nums = new int[]{1,2,3,4,5,6,7};
        int k = 3;
        new Solution().rotate(nums, k);
        Object actual = nums;
        String expected = "[5,6,7,1,2,3,4]";
        String result = canonical(actual);
        System.out.println("Input: " + "nums=[1,2,3,4,5,6,7]; k=3");
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
        public void rotate(int[] nums, int k) {
            // 结果直接写回nums，三次反转完成右移
            k %= nums.length;
            // 整体反转后分别恢复两段内部顺序，AB变成BA
            reverse(nums, 0, nums.length - 1); // 先反转整个数组
            reverse(nums, 0, k - 1); // 恢复前k个元素的顺序
            reverse(nums, k, nums.length - 1); // 恢复剩余元素的顺序
        }

        private void reverse(int[] nums, int left, int right) {
            while (left < right) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
                left++;
                right--;
            }
        }
    }

    // 保留此前版本，便于对照；main和Hot100调试器使用上方的清晰写法。
    static class SolutionOriginal {
        public void rotate(int[] nums, int k) {
            k %= nums.length;
            // 整体反转后分别恢复两段内部顺序，AB变成BA
            reverse(nums, 0, nums.length - 1);
            reverse(nums, 0, k - 1);
            reverse(nums, k, nums.length - 1);
        }

        private void reverse(int[] nums, int left, int right) {
            while (left < right) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
                left++;
                right--;
            }
        }
    }
}
