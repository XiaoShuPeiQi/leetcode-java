package leetcode.array;

import java.util.*;

public class LC0035SearchInsertPosition {
    public static void main(String[] args) {
        // 来源：数组章节本次修订的实例走过程。
        int[] nums = new int[]{1,3,5,6};
        int target = 2;
        int expected = 1;
        int actual = new Solution().searchInsert(nums, target);
        System.out.println("Input: nums = [1, 3, 5, 6], target = 2");
        System.out.println("Expected:");
        System.out.println(expected);
        System.out.println("Actual:");
        System.out.println(actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) {
            throw new AssertionError("结果与预期不同");
        }
    }

    static class Solution {
        public int searchInsert(int[] nums, int target) {
            int left = 0;
            int right = nums.length - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (nums[mid] < target) {
                    left = mid + 1;
                } else if (nums[mid] > target) {
                    right = mid - 1;
                } else {
                    return mid;
                }
            }
            return left; // 未命中时，left就是插入位置
        }
    }
}
