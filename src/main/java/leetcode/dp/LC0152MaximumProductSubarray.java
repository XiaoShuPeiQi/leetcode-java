package leetcode.dp;

import common.TreeSupport;

public class LC0152MaximumProductSubarray {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "nums = [-2, 3, -4]",
                24, runSolution());
    }

    // 本节推演选用的小用例。
    static Object runSolution() {
        int[] nums = {-2, 3, -4};
        return new Solution().maxProduct(nums);
    }

    static class Solution {
        public int maxProduct(int[] nums) {
            int maxEnding = nums[0];
            int minEnding = nums[0];
            int result = nums[0];

            for (int i = 1; i < nums.length; i++) {
                int previousMax = maxEnding;
                maxEnding = Math.max(nums[i], Math.max(previousMax * nums[i], minEnding * nums[i]));
                minEnding = Math.min(nums[i], Math.min(previousMax * nums[i], minEnding * nums[i]));
                result = Math.max(result, maxEnding);
            }
            return result;
        }
    }
}
