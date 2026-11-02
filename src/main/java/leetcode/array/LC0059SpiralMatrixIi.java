package leetcode.array;

import java.util.*;

public class LC0059SpiralMatrixIi {
    public static void main(String[] args) {
        // 来源：数组章节本次修订的实例走过程。
        int n = 3;
        int[][] expected = new int[][]{{1,2,3},{8,9,4},{7,6,5}};
        int[][] actual = new Solution().generateMatrix(n);
        System.out.println("Input: n = 3");
        System.out.println("Expected:");
        System.out.println(Arrays.deepToString(expected));
        System.out.println("Actual:");
        System.out.println(Arrays.deepToString(actual));
        boolean passed = Arrays.deepEquals(actual, expected);
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) {
            throw new AssertionError("结果与预期不同");
        }
    }

    static class Solution {
        public int[][] generateMatrix(int n) {
            int[][] result = new int[n][n];
            int start = 0;
            int end = n - 1;
            int value = 1;
            while (start < end) { // 四条边都把终点留给下一条边
                for (int col = start; col < end; col++) {
                    result[start][col] = value++;
                }
                for (int row = start; row < end; row++) {
                    result[row][end] = value++;
                }
                for (int col = end; col > start; col--) {
                    result[end][col] = value++;
                }
                for (int row = end; row > start; row--) {
                    result[row][start] = value++;
                }
                start++;
                end--;
            }
            if (start == end) {
                result[start][start] = value; // 奇数阶剩下中心
            }
            return result;
        }
    }
}
