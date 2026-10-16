package leetcode.dp;

import java.util.*;

public class LC0213HouseRobberII {

    public static void main(String[] args) {
        // 测试来源：动态规划笔记中的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe
        int[] nums = new int[]{2,3,2};
        int expected = 3;
        int actual = new Solution().rob(nums);
        System.out.println("Input:\n" + "nums = [2, 3, 2]");
        System.out.println("Expected:\n" + expected);
        System.out.println("Actual:\n" + actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) throw new AssertionError("结果与预期不同");
    }

    static class Solution {
        public int rob(int[] nums) {
            if (nums.length == 1) return nums[0];
            return Math.max(robRange(nums, 0, nums.length - 2),
                    robRange(nums, 1, nums.length - 1));
        }

        private int robRange(int[] nums, int left, int right) {
            int len = right - left + 1;
            int[] dp = new int[len + 1];
            dp[1] = nums[left];
            for (int i = 2; i <= len; i++) {
                dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[left + i - 1]);
            }
            return dp[len];
        }
    }
}
