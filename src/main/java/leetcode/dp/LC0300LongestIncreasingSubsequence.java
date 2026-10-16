package leetcode.dp;

import java.util.*;

public class LC0300LongestIncreasingSubsequence {

    public static void main(String[] args) {
        // 测试来源：动态规划笔记中的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe
        int[] nums = new int[]{10,9,2,5,3,7,101,18};
        int expected = 4;
        int actual = new Solution().lengthOfLIS(nums);
        System.out.println("Input:\n" + "nums = [10, 9, 2, 5, 3, 7, 101, 18]");
        System.out.println("Expected:\n" + expected);
        System.out.println("Actual:\n" + actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) throw new AssertionError("结果与预期不同");
    }

    static class Solution {
        public int lengthOfLIS(int[] nums) {
            int[] dp = new int[nums.length];
            int result = 0;
            for (int i = 0; i < nums.length; i++) {
                dp[i] = 1;
                for (int j = 0; j < i; j++) {
                    if (nums[j] < nums[i]) dp[i] = Math.max(dp[i], dp[j] + 1);
                }
                result = Math.max(result, dp[i]);
            }
            return result;
        }
    }
}
