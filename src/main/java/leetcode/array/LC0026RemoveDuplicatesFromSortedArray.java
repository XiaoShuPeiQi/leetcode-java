package leetcode.array;

import java.util.*;

public class LC0026RemoveDuplicatesFromSortedArray {
    public static void main(String[] args) {
        // 来源：数组章节本次修订的实例走过程。
        int[] nums = new int[]{0,0,1,1,2};
        int[] expected = new int[]{0,1,2};
        int size = new Solution().removeDuplicates(nums);
        int[] actual = Arrays.copyOf(nums, size);
        System.out.println("Input: nums = [0, 0, 1, 1, 2]");
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
        public int removeDuplicates(int[] nums) {
            if (nums.length == 0) {
                return 0;
            }
            int slow = 1; // 非空时，第一个数一定保留
            for (int fast = 1; fast < nums.length; fast++) {
                if (nums[fast] != nums[slow - 1]) {
                    nums[slow] = nums[fast];
                    slow++;
                }
            }
            return slow;
        }
    }
}
