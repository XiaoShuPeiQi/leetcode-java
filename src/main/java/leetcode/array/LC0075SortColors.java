package leetcode.array;

import common.TreeSupport;

public class LC0075SortColors {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "nums = [2, 0, 2, 1, 1, 0]",
                new int[]{0, 0, 1, 1, 2, 2}, runSolution());
    }

    // 力扣官方示例，与本节推演一致。
    static Object runSolution() {
        int[] nums = {2, 0, 2, 1, 1, 0};
        new Solution().sortColors(nums);
        return nums;
    }

    static class Solution {
        public void sortColors(int[] nums) {
            int left = 0;
            int right = nums.length - 1;
            int i = 0;

            while (i <= right) {
                if (nums[i] == 0) {
                    swap(nums, i, left);
                    left++;
                    i++;
                } else if (nums[i] == 2) {
                    swap(nums, i, right);
                    right--;
                } else {
                    i++;
                }
            }
        }

        private void swap(int[] nums, int i, int j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }
}
