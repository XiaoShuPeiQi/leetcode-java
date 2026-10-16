package leetcode.dp;

import java.util.*;

public class LC0416PartitionEqualSubsetSum {

    public static void main(String[] args) {
        // 测试来源：动态规划笔记中的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe
        int[] nums = new int[]{1,5,11,5};
        boolean expected = true;
        boolean actual = new Solution().canPartition(nums);
        System.out.println("Input:\n" + "nums = [1, 5, 11, 5]");
        System.out.println("Expected:\n" + expected);
        System.out.println("Actual:\n" + actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) throw new AssertionError("结果与预期不同");
    }

    static class Solution {
        public boolean canPartition(int[] nums) {
            int sum = 0;
            for (int num : nums) sum += num;
            if (sum % 2 != 0) return false;
            int target = sum / 2;
            boolean[] dp = new boolean[target + 1];
            dp[0] = true;
            for (int num : nums) {
                for (int j = target; j >= num; j--) {
                    dp[j] = dp[j] || dp[j - num]; // 倒序：当前数最多选一次
                }
            }
            return dp[target];
        }
    }
}
