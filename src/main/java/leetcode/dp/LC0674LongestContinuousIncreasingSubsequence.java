package leetcode.dp;

import java.util.*;

public class LC0674LongestContinuousIncreasingSubsequence {

    public static void main(String[] args) {
        // 测试来源：动态规划笔记中的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe
        int[] nums = new int[]{1,3,5,4,7};
        int expected = 3;
        int actual = new Solution().findLengthOfLCIS(nums);
        System.out.println("Input:\n" + "nums = [1, 3, 5, 4, 7]");
        System.out.println("Expected:\n" + expected);
        System.out.println("Actual:\n" + actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) throw new AssertionError("结果与预期不同");
    }

    static class Solution {
        public int findLengthOfLCIS(int[] nums) {
            int[] dp = new int[nums.length];
            int result = 0;
            for (int i = 0; i < nums.length; i++) {
                dp[i] = 1;
                if (i > 0 && nums[i] > nums[i - 1]) dp[i] = dp[i - 1] + 1;
                result = Math.max(result, dp[i]);
            }
            return result;
        }
    }
}
