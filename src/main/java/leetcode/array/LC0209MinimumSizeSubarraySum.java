package leetcode.array;

import java.util.*;

public class LC0209MinimumSizeSubarraySum {
    public static void main(String[] args) {
        // 来源：数组章节本次修订的实例走过程。
        int target = 7;
        int[] nums = new int[]{2,3,1,2,4,3};
        int expected = 2;
        int actual = new Solution().minSubArrayLen(target, nums);
        System.out.println("Input: target = 7, nums = [2, 3, 1, 2, 4, 3]");
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
        public int minSubArrayLen(int target, int[] nums) {
            int left = 0;
            int result = Integer.MAX_VALUE;
            long sum = 0;
            for (int right = 0; right < nums.length; right++) {
                sum += nums[right];
                while (sum >= target) { // 达标后，先记答案再尝试缩短
                    result = Math.min(result, right - left + 1);
                    sum -= nums[left];
                    left++;
                }
            }
            return result == Integer.MAX_VALUE ? 0 : result;
        }
    }
}
