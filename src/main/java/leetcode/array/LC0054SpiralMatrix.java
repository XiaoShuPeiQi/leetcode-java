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
            List<Integer> res = new ArrayList<>(); // 按螺旋访问顺序依次加入元素
            if (matrix.length == 0 || matrix[0].length == 0) {
                return res;
            }
            int top = 0;
            int bottom = matrix.length - 1;
            int left = 0;
            int right = matrix[0].length - 1;
            while (top <= bottom && left <= right) {
                for (int col = left; col <= right; col++) {
                    res.add(matrix[top][col]);
                }
                top++;
                for (int row = top; row <= bottom; row++) {
                    res.add(matrix[row][right]);
                }
                right--;
                if (top <= bottom) { // 仍有未读行，才走下边
                    for (int col = right; col >= left; col--) {
                        res.add(matrix[bottom][col]);
                    }
                    bottom--;
                }
                if (left <= right) { // 仍有未读列，才走左边
                    for (int row = bottom; row >= top; row--) {
                        res.add(matrix[row][left]);
                    }
                    left++;
                }
            }
            return res;
        }
    }

    // 保留此前版本，便于对照；main和Hot100调试器使用上方的清晰写法。
    static class SolutionOriginal {
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
