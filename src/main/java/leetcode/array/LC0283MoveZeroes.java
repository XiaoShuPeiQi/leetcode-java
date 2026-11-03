package leetcode.array;

import java.util.*;

public class LC0283MoveZeroes {
    public static void main(String[] args) {
        // 来源：数组章节本次修订的实例走过程。
        int[] nums = new int[]{0,1,0,3,12};
        int[] expected = new int[]{1,3,12,0,0};
        new Solution().moveZeroes(nums);
        int[] actual = nums;
        System.out.println("Input: nums = [0, 1, 0, 3, 12]");
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
        public void moveZeroes(int[] nums) {
            // 结果直接写回nums：先收集非零元素，再在剩余位置补零
            int slow = 0;
            for (int fast = 0; fast < nums.length; fast++) {
                if (nums[fast] != 0) {
                    nums[slow] = nums[fast];
                    slow++;
                }
            }
            // 有效前缀之外必须全部补零
            for (int i = slow; i < nums.length; i++) {
                nums[i] = 0;
            }
        }
    }

    // 保留此前版本，便于对照；main和Hot100调试器使用上方的清晰写法。
    static class SolutionOriginal {
        public void moveZeroes(int[] nums) {
            int slow = 0;
            for (int fast = 0; fast < nums.length; fast++) {
                if (nums[fast] != 0) {
                    nums[slow] = nums[fast];
                    slow++;
                }
            }
            // 有效前缀之外必须全部补零
            for (int i = slow; i < nums.length; i++) {
                nums[i] = 0;
            }
        }
    }
}
