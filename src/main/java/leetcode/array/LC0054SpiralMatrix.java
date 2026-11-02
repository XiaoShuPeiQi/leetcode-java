package leetcode.array;

import java.util.*;

public class LC0054SpiralMatrix {
    public static void main(String[] args) {
        // 来源：数组章节本次修订的实例走过程。
        int[][] matrix = new int[][]{{1,2,3},{4,5,6},{7,8,9}};
        List<Integer> expected = Arrays.asList(1,2,3,6,9,8,7,4,5);
        List<Integer> actual = new Solution().spiralOrder(matrix);
        System.out.println("Input: matrix = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]");
        System.out.println("Expected:");
        System.out.println(expected);
        System.out.println("Actual:");
        System.out.println(actual);
        boolean passed = actual.equals(expected);
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) {
            throw new AssertionError("结果与预期不同");
        }
    }

    static class Solution {
        public List<Integer> spiralOrder(int[][] matrix) {
            List<Integer> result = new ArrayList<>();
            if (matrix.length == 0 || matrix[0].length == 0) {
                return result;
            }
            int top = 0;
            int bottom = matrix.length - 1;
            int left = 0;
            int right = matrix[0].length - 1;
            while (top <= bottom && left <= right) {
                for (int col = left; col <= right; col++) {
                    result.add(matrix[top][col]);
                }
                top++;
                for (int row = top; row <= bottom; row++) {
                    result.add(matrix[row][right]);
                }
                right--;
                if (top <= bottom) { // 仍有未读行，才走下边
                    for (int col = right; col >= left; col--) {
                        result.add(matrix[bottom][col]);
                    }
                    bottom--;
                }
                if (left <= right) { // 仍有未读列，才走左边
                    for (int row = bottom; row >= top; row--) {
                        result.add(matrix[row][left]);
                    }
                    left++;
                }
            }
            return result;
        }
    }
}
