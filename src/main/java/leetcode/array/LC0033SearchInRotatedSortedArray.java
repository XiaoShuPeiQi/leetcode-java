package leetcode.array;

import common.TreeSupport;

public class LC0033SearchInRotatedSortedArray {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "nums = [4, 5, 6, 7, 0, 1, 2], target = 0",
                4, runSolution());
    }

    // 测试来源：飞书 Hot100 第 66 题的实例走过程。
    static Object runSolution() {
        int[] nums = {4, 5, 6, 7, 0, 1, 2};
        int target = 0;
        return new Solution().search(nums, target);
    }

    static class Solution {
        public int search(int[] nums, int target) {
            int left = 0, right = nums.length - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (nums[mid] == target) return mid;

                if (nums[left] <= nums[mid]) { // 左半区有序
                    if (nums[left] <= target && target < nums[mid]) right = mid - 1;
                    else left = mid + 1;
                } else { // 右半区有序
                    if (nums[mid] < target && target <= nums[right]) left = mid + 1;
                    else right = mid - 1;
                }
            }
            return -1;
        }
    }
}
