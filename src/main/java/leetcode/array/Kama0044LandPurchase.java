package leetcode.array;

import java.util.*;

public class Kama0044LandPurchase {
    public static void main(String[] args) {
        // 来源：数组章节本次修订的实例走过程。
        int[][] land = new int[][]{{1,2,3},{2,1,3},{1,2,3}};
        long expected = 0;
        long actual = runSolution(land);
        System.out.println("Input: land = [[1, 2, 3], [2, 1, 3], [1, 2, 3]]");
        System.out.println("Expected:");
        System.out.println(expected);
        System.out.println("Actual:");
        System.out.println(actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) {
            throw new AssertionError("结果与预期不同");
        }
    }

    private static long runSolution(int[][] land) {
        long[] rowSum = new long[land.length];
        long[] colSum = new long[land[0].length];
        long total = 0;
        for (int row = 0; row < land.length; row++) {
            for (int col = 0; col < land[0].length; col++) {
                rowSum[row] += land[row][col];
                colSum[col] += land[row][col];
                total += land[row][col];
            }
        }
        return Solution.minDifference(rowSum, colSum, total);
    }

    static class Solution {
        static long minDifference(long[] rowSum, long[] colSum, long total) {
            long result = Long.MAX_VALUE;
            long part = 0;
            // 最后一行之后不是合法切线，两边必须非空
            for (int row = 0; row < rowSum.length - 1; row++) {
                part += rowSum[row];
                result = Math.min(result, Math.abs(part - (total - part)));
            }
            part = 0;
            for (int col = 0; col < colSum.length - 1; col++) {
                part += colSum[col];
                result = Math.min(result, Math.abs(part - (total - part)));
            }
            return result;
        }
    }
}
