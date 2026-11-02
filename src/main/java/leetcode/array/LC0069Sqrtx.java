package leetcode.array;

import java.util.*;

public class LC0069Sqrtx {
    public static void main(String[] args) {
        // 来源：数组章节本次修订的实例走过程。
        int x = 8;
        int expected = 2;
        int actual = new Solution().mySqrt(x);
        System.out.println("Input: x = 8");
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
        public int mySqrt(int x) {
            int left = 0;
            int right = x;
            int answer = 0;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                long square = (long) mid * mid; // 先转long，再乘
                if (square <= x) {
                    answer = mid;
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            return answer;
        }
    }
}
