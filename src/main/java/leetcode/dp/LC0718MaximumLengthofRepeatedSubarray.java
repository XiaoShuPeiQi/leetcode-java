package leetcode.dp;

import java.util.*;

public class LC0718MaximumLengthofRepeatedSubarray {

    public static void main(String[] args) {
        // 测试来源：动态规划笔记中的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe
        int[] nums1 = new int[]{1,2,3,2,1};
        int[] nums2 = new int[]{3,2,1,4,7};
        int expected = 3;
        int actual = new Solution().findLength(nums1, nums2);
        System.out.println("Input:\n" + "nums1 = [1, 2, 3, 2, 1], nums2 = [3, 2, 1, 4, 7]");
        System.out.println("Expected:\n" + expected);
        System.out.println("Actual:\n" + actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) throw new AssertionError("结果与预期不同");
    }

    static class Solution {
        public int findLength(int[] nums1, int[] nums2) {
            int[][] dp = new int[nums1.length + 1][nums2.length + 1];
            int result = 0;
            for (int i = 1; i <= nums1.length; i++) {
                for (int j = 1; j <= nums2.length; j++) {
                    if (nums1[i - 1] == nums2[j - 1]) dp[i][j] = dp[i - 1][j - 1] + 1;
                    result = Math.max(result, dp[i][j]);
                }
            }
            return result;
        }
    }
}
