package leetcode.array;

import java.util.*;

public class LC0027RemoveElement {
    public static void main(String[] args) {
        // 来源：数组章节本次修订的实例走过程。
        int[] nums = new int[]{3,2,2,3};
        int val = 3;
        int[] expected = new int[]{2,2};
        int size = new Solution().removeElement(nums, val);
        int[] actual = Arrays.copyOf(nums, size);
        System.out.println("Input: nums = [3, 2, 2, 3], val = 3");
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
        public int removeElement(int[] nums, int val) {
            int slow = 0; // 下次写入位置，也是有效长度
            for (int fast = 0; fast < nums.length; fast++) {
                if (nums[fast] != val) {
                    nums[slow] = nums[fast];
                    slow++;
                }
            }
            return slow;
        }
    }
}
