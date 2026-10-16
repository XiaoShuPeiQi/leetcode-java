package leetcode.dp;

import java.util.*;

public class LC0377CombinationSumIV {

    public static void main(String[] args) {
        // 测试来源：动态规划笔记中的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe
        int[] nums = new int[]{1,2,3};
        int target = 4;
        int expected = 7;
        int actual = new Solution().combinationSum4(nums, target);
        System.out.println("Input:\n" + "nums = [1, 2, 3], target = 4");
        System.out.println("Expected:\n" + expected);
        System.out.println("Actual:\n" + actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) throw new AssertionError("结果与预期不同");
    }

    static class Solution {
        public int combinationSum4(int[] nums, int target) {
            long[] dp = new long[target + 1];
            dp[0] = 1;
            for (int i = 1; i <= target; i++) {
                for (int num : nums) {
                    if (num <= i) {
                        dp[i] = Math.min(Integer.MAX_VALUE, dp[i] + dp[i - num]);
                    }
                }
            }
            return (int) dp[target];
        }
    }
}
