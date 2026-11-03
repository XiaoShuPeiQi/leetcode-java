package leetcode.array;

import java.util.*;

public class LC0011ContainerWithMostWater {
    public static void main(String[] args) {
        // 测试来源：Hot100笔记中相同的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf
        int[] height = new int[]{1,8,6,2,5,4,8,3,7};
        Object actual = new Solution().maxArea(height);
        String expected = "49";
        String result = canonical(actual);
        System.out.println("Input: " + "height=[1,8,6,2,5,4,8,3,7]");
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
        public int maxArea(int[] height) {
            int res = 0; // 保存最大容积
            int left = 0;
            int right = height.length - 1;

            while (left < right) {
                int shorterHeight = Math.min(height[left], height[right]);
                int width = right - left;
                int area = shorterHeight * width;
                if (area > res) {
                    res = area;
                }

                // 面积受短板限制，移动短板才可能找到更大容积
                if (height[left] <= height[right]) {
                    left++;
                } else {
                    right--;
                }
            }
            return res;
        }
    }

    // 保留此前版本，便于对照；main和Hot100调试器使用上方的清晰写法。
    static class SolutionOriginal {
        public int maxArea(int[] height) {
            int left = 0;
            int right = height.length - 1;
            int result = 0;
            while (left < right) {
                int area = Math.min(height[left], height[right]) * (right - left);
                result = Math.max(result, area);
                if (height[left] <= height[right]) { // 保留短板只会让宽度变小，移动短板才可能改善
                    left++;
                } else {
                    right--;
                }
            }
            return result;
        }
    }
}
