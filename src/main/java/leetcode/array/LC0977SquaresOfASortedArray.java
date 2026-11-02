package leetcode.array;

import java.util.*;

public class LC0977SquaresOfASortedArray {
    public static void main(String[] args) {
        // 来源：数组章节本次修订的实例走过程。
        int[] nums = new int[]{-4,-1,0,3,10};
        int[] expected = new int[]{0,1,9,16,100};
        int[] actual = new Solution().sortedSquares(nums);
        System.out.println("Input: nums = [-4, -1, 0, 3, 10]");
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
        public int[] sortedSquares(int[] nums) {
            int[] result = new int[nums.length];
            int left = 0;
            int right = nums.length - 1;
            // 两端选最大平方，倒着填结果
            for (int write = nums.length - 1; write >= 0; write--) {
                int leftSquare = nums[left] * nums[left];
                int rightSquare = nums[right] * nums[right];
                if (leftSquare > rightSquare) {
                    result[write] = leftSquare;
                    left++;
                } else {
                    result[write] = rightSquare;
                    right--;
                }
            }
            return result;
        }
    }
}
