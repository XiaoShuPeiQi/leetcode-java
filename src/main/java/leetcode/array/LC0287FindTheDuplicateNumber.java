package leetcode.array;

import common.TreeSupport;

public class LC0287FindTheDuplicateNumber {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "nums = [1, 3, 4, 2, 2]",
                2, runSolution());
    }

    // 力扣官方示例，与本节推演一致。
    static Object runSolution() {
        int[] nums = {1, 3, 4, 2, 2};
        return new Solution().findDuplicate(nums);
    }

    static class Solution {
        public int findDuplicate(int[] nums) {
            int slow = 0;
            int fast = 0;
            do {
                slow = nums[slow];
                fast = nums[nums[fast]];
            } while (slow != fast);

            slow = 0;
            while (slow != fast) {
                slow = nums[slow];
                fast = nums[fast];
            }
            return slow;
        }
    }
}
