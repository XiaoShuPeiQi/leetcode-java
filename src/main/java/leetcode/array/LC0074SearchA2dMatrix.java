package leetcode.array;

import common.TreeSupport;

public class LC0074SearchA2dMatrix {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "matrix = [[1, 3, 5], [7, 9, 11]], target = 9",
                true, runSolution());
    }

    // 测试来源：飞书 Hot100 第 64 题的实例走过程。
    static Object runSolution() {
        int[][] matrix = {{1, 3, 5}, {7, 9, 11}};
        int target = 9;
        return new Solution().searchMatrix(matrix, target);
    }

    static class Solution {
        public boolean searchMatrix(int[][] matrix, int target) {
            int rows = matrix.length;
            int cols = matrix[0].length;
            int left = 0, right = rows * cols - 1;

            while (left <= right) {
                int mid = left + (right - left) / 2;
                int row = mid / cols;
                int col = mid % cols;
                int value = matrix[row][col];

                if (value < target) {
                    left = mid + 1;
                } else if (value > target) {
                    right = mid - 1;
                } else {
                    return true;
                }
            }
            return false;
        }
    }
}
