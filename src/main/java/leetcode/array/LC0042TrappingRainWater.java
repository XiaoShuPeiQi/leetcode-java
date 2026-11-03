package leetcode.array;

import java.util.*;

public class LC0042TrappingRainWater {
    public static void main(String[] args) {
        // 测试来源：Hot100笔记中相同的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf
        int[] height = new int[]{0,1,0,2,1,0,1,3,2,1,2,1};
        Object actual = new Solution().trap(height);
        String expected = "6";
        String result = canonical(actual);
        System.out.println("Input: " + "height=[0,1,0,2,1,0,1,3,2,1,2,1]");
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
        public int trap(int[] height) {
            int res = 0; // 累加每一列的接水量
            int left = 0;
            int right = height.length - 1;
            int leftMax = 0;
            int rightMax = 0;

            while (left <= right) {
                leftMax = Math.max(leftMax, height[left]);
                rightMax = Math.max(rightMax, height[right]);

                if (leftMax <= rightMax) { // 右侧足够高，可以确定左侧这一列的水量
                    res += leftMax - height[left];
                    left++;
                } else {
                    res += rightMax - height[right];
                    right--;
                }
            }
            return res;
        }
    }

    // 保留此前版本，便于对照；main和Hot100调试器使用上方的清晰写法。
    static class SolutionOriginal {
        public int trap(int[] height) {
            int left = 0;
            int right = height.length - 1;
            int leftMax = 0;
            int rightMax = 0;
            int result = 0;
            while (left <= right) {
                leftMax = Math.max(leftMax, height[left]);
                rightMax = Math.max(rightMax, height[right]);
                if (leftMax <= rightMax) { // 右侧已有足够高的板，本列只受leftMax限制
                    result += leftMax - height[left];
                    left++;
                } else {
                    result += rightMax - height[right];
                    right--;
                }
            }
            return result;
        }
    }
}
