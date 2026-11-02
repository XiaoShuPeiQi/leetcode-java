package leetcode.array;

import java.util.*;

public class LC0904FruitIntoBaskets {
    public static void main(String[] args) {
        // 来源：数组章节本次修订的实例走过程。
        int[] fruits = new int[]{1,2,3,2,2};
        int expected = 4;
        int actual = new Solution().totalFruit(fruits);
        System.out.println("Input: fruits = [1, 2, 3, 2, 2]");
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
        public int totalFruit(int[] fruits) {
            Map<Integer, Integer> count = new HashMap<>();
            int left = 0;
            int result = 0;
            for (int right = 0; right < fruits.length; right++) {
                int fruit = fruits[right];
                count.put(fruit, count.getOrDefault(fruit, 0) + 1);
                while (count.size() > 2) { // 超过两种才缩，缩到有效再记最长
                    int removed = fruits[left];
                    count.put(removed, count.get(removed) - 1);
                    if (count.get(removed) == 0) {
                        count.remove(removed);
                    }
                    left++;
                }
                result = Math.max(result, right - left + 1);
            }
            return result;
        }
    }
}
