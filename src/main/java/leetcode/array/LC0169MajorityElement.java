package leetcode.array;

import common.TreeSupport;

public class LC0169MajorityElement {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "nums = [2, 2, 1, 1, 1, 2, 2]",
                2, runSolution());
    }

    // 力扣官方示例，与本节推演一致。
    static Object runSolution() {
        int[] nums = {2, 2, 1, 1, 1, 2, 2};
        return new Solution().majorityElement(nums);
    }

    static class Solution {
        public int majorityElement(int[] nums) {
            int candidate = 0;
            int votes = 0;
            for (int num : nums) {
                if (votes == 0) {
                    candidate = num;
                }
                votes += num == candidate ? 1 : -1;
            }
            return candidate;
        }
    }
}
