package leetcode.dp;

import java.util.*;

public class LC0494TargetSum {

    public static void main(String[] args) {
        // 测试来源：动态规划笔记中的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe
        int[] nums = new int[]{1,1,1,1,1};
        int target = 3;
        int expected = 5;
        int actual = new Solution().findTargetSumWays(nums, target);
        System.out.println("Input:\n" + "nums = [1, 1, 1, 1, 1], target = 3");
        System.out.println("Expected:\n" + expected);
        System.out.println("Actual:\n" + actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) throw new AssertionError("结果与预期不同");
    }

    static class Solution {
        public int findTargetSumWays(int[] nums, int target) {
            int sum = 0;
            for (int num : nums) sum += num;
            if (Math.abs(target) > sum || (sum + target) % 2 != 0) return 0;
            int bag = (sum + target) / 2;
            int[] dp = new int[bag + 1];
            dp[0] = 1;
            for (int num : nums) {
                for (int j = bag; j >= num; j--) {
                    dp[j] += dp[j - num]; // num=0 时也要更新，正负零是两种方案
                }
            }
            return dp[bag];
        }
    }
}
