package leetcode.array;

import common.TreeSupport;

public class LC0136SingleNumber {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "nums = [4, 1, 2, 1, 2]",
                4, runSolution());
    }

    // 力扣官方示例，与本节推演一致。
    static Object runSolution() {
        int[] nums = {4, 1, 2, 1, 2};
        return new Solution().singleNumber(nums);
    }

    static class Solution {
        public int singleNumber(int[] nums) {
            int result = 0;
            for (int num : nums) {
                result ^= num;
            }
            return result;
        }
    }
}
