package leetcode.array;

import java.util.*;

public class LC0367ValidPerfectSquare {
    public static void main(String[] args) {
        // 来源：数组章节本次修订的实例走过程。
        int num = 16;
        boolean expected = true;
        boolean actual = new Solution().isPerfectSquare(num);
        System.out.println("Input: num = 16");
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
        public boolean isPerfectSquare(int num) {
            int left = 1;
            int right = num;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                long square = (long) mid * mid;
                if (square < num) {
                    left = mid + 1;
                } else if (square > num) {
                    right = mid - 1;
                } else {
                    return true; // 本题只接受精确命中
                }
            }
            return false;
        }
    }
}
