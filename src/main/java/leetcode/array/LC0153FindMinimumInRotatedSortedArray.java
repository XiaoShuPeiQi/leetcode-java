package leetcode.array;

import common.TreeSupport;

public class LC0153FindMinimumInRotatedSortedArray {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "nums = [4, 5, 6, 7, 0, 1, 2]",
                0, runSolution());
    }

    // 测试来源：飞书 Hot100 第 67 题的实例走过程。
    static Object runSolution() {
        int[] nums = {4, 5, 6, 7, 0, 1, 2};
        return new Solution().findMin(nums);
    }

    static class Solution {
        public int findMin(int[] nums) {
            int left = 0;
            int right = nums.length - 1;

            while (left < right) {
                int mid = left + (right - left) / 2;

                if (nums[mid] > nums[right]) {
                    // mid在前半段（较大元素），最小值在右边
                    left = mid + 1;
                } else {
                    // mid在后半段（较小元素），最小值在左边或就是mid
                    right = mid;
                }
            }

            return nums[left];
        }
    }
}
