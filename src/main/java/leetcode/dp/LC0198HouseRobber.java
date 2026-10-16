package leetcode.dp;

import java.util.*;

public class LC0198HouseRobber {

    public static void main(String[] args) {
        // 测试来源：动态规划笔记中的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe
        int[] nums = new int[]{2,7,9,3,1};
        int expected = 12;
        int actual = new Solution().rob(nums);
        System.out.println("Input:\n" + "nums = [2, 7, 9, 3, 1]");
        System.out.println("Expected:\n" + expected);
        System.out.println("Actual:\n" + actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) throw new AssertionError("结果与预期不同");
    }

    static class Solution {
        public int rob(int[] nums) {
            int n = nums.length;
            if (n == 0) return 0;
            int[] dp = new int[n + 1];
            dp[1] = nums[0];
            for (int i = 2; i <= n; i++) {
                dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i - 1]);
            }
            return dp[n];
        }
    }
}
