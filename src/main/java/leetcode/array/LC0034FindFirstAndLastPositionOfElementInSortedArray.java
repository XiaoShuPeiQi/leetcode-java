package leetcode.array;

import java.util.*;

public class LC0034FindFirstAndLastPositionOfElementInSortedArray {
    public static void main(String[] args) {
        // 来源：数组章节本次修订的实例走过程。
        int[] nums = new int[]{5,7,7,8,8,10};
        int target = 8;
        int[] expected = new int[]{3,4};
        int[] actual = new Solution().searchRange(nums, target);
        System.out.println("Input: nums = [5, 7, 7, 8, 8, 10], target = 8");
        System.out.println("Expected:");
        System.out.println(Arrays.toString(expected));
        System.out.println("Actual:");
        System.out.println(Arrays.toString(actual));
        boolean passed = Arrays.equals(actual, expected);
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) {
            throw new AssertionError("结果与预期不同");
        }
    }

    static class Solution {
        public int[] searchRange(int[] nums, int target) {
            int first = findBoundary(nums, target, true);
            int last = findBoundary(nums, target, false);
            return new int[]{first, last};
        }

        private int findBoundary(int[] nums, int target, boolean findLeft) {
            int left = 0;
            int right = nums.length - 1;
            int answer = -1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (nums[mid] < target) {
                    left = mid + 1;
                } else if (nums[mid] > target) {
                    right = mid - 1;
                } else {
                    answer = mid; // 先保存命中，再继续找边界
                    if (findLeft) {
                        right = mid - 1;
                    } else {
                        left = mid + 1;
                    }
                }
            }
            return answer;
        }
    }
}
