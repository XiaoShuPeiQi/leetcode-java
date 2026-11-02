package leetcode.array;

import java.util.*;

public class LCR0146SpiralArray {
    public static void main(String[] args) {
        // 来源：数组章节本次修订的实例走过程。
        int[][] array = new int[][]{{1,2,3},{4,5,6},{7,8,9}};
        int[] expected = new int[]{1,2,3,6,9,8,7,4,5};
        int[] actual = new Solution().spiralArray(array);
        System.out.println("Input: array = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]");
        System.out.println("Expected:");
        System.out.println(Arrays.toString(expected));
        System.out.println("Actual:");
        System.out.println(Arrays.toString(actual));
        boolean passed = Arrays.equals(actual, expected);
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) {
            throw new AssertionError("结果与预期不同");
        }
    }

    static class Solution {
        public int[] spiralArray(int[][] array) {
            if (array.length == 0 || array[0].length == 0) {
                return new int[0];
            }
            int[] result = new int[array.length * array[0].length];
            int write = 0;
            int top = 0;
            int bottom = array.length - 1;
            int left = 0;
            int right = array[0].length - 1;
            while (top <= bottom && left <= right) {
                for (int col = left; col <= right; col++) {
                    result[write++] = array[top][col];
                }
                top++;
                for (int row = top; row <= bottom; row++) {
                    result[write++] = array[row][right];
                }
                right--;
                if (top <= bottom) { // 防止只剩一行时重复读取
                    for (int col = right; col >= left; col--) {
                        result[write++] = array[bottom][col];
                    }
                    bottom--;
                }
                if (left <= right) { // 防止只剩一列时重复读取
                    for (int row = bottom; row >= top; row--) {
                        result[write++] = array[row][left];
                    }
                    left++;
                }
            }
            return result;
        }
    }
}
