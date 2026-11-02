package leetcode.array;

import java.util.*;

public class Kama0058RangeSum {
    public static void main(String[] args) {
        // 来源：数组章节本次修订的实例走过程。
        int[] nums = new int[]{1,2,3,4,5};
        int[][] queries = new int[][]{{0,1},{1,3}};
        long[] expected = new long[]{3L,9L};
        long[] actual = runSolution(nums, queries);
        System.out.println("Input: nums = [1, 2, 3, 4, 5], queries = [[0, 1], [1, 3]]");
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

    private static long[] runSolution(int[] nums, int[][] queries) {
        long[] prefix = Solution.buildPrefix(nums);
        long[] result = new long[queries.length];
        for (int i = 0; i < queries.length; i++) {
            result[i] = Solution.rangeSum(prefix, queries[i][0], queries[i][1]);
        }
        return result;
    }

    static class Solution {
        static long[] buildPrefix(int[] nums) {
            long[] prefix = new long[nums.length + 1];
            for (int i = 0; i < nums.length; i++) {
                prefix[i + 1] = prefix[i] + nums[i];
            }
            return prefix;
        }

        static long rangeSum(long[] prefix, int left, int right) {
            return prefix[right + 1] - prefix[left];
        }
    }
}
