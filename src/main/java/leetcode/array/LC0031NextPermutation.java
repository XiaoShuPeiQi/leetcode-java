package leetcode.array;

import common.TreeSupport;

public class LC0031NextPermutation {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "nums = [1, 3, 5, 4, 2]",
                new int[]{1, 4, 2, 3, 5}, runSolution());
    }

    // 本节推演选用的小用例。
    static Object runSolution() {
        int[] nums = {1, 3, 5, 4, 2};
        new Solution().nextPermutation(nums);
        return nums;
    }

    static class Solution {
        public void nextPermutation(int[] nums) {
            int pivot = nums.length - 2;
            while (pivot >= 0 && nums[pivot] >= nums[pivot + 1]) {
                pivot--;
            }

            if (pivot >= 0) {
                int next = nums.length - 1;
                while (nums[next] <= nums[pivot]) {
                    next--;
                }
                swap(nums, pivot, next);
            }

            for (int left = pivot + 1, right = nums.length - 1; left < right; left++, right--) {
                swap(nums, left, right);
            }
        }

        private void swap(int[] nums, int i, int j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }
}
