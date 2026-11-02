package leetcode.hashtable;

import java.util.*;

public class LC0202HappyNumber {

    public static void main(String[] args) {
        // 测试来源：飞书哈希表笔记的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/F4qJw6Pr2if4LkkJBjBc8aM3nXd
        int n = 19;
        boolean expected = true;
        boolean actual = new Solution().isHappy(n);
        System.out.println("Input: n = 19");
        System.out.println("Expected: " + expected);
        System.out.println("Actual: " + actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) {
            throw new AssertionError("结果与预期不同");
        }
    }

    static class Solution {
        public boolean isHappy(int n) {
            Set<Integer> seen = new HashSet<>();
            while (n != 1) {
                if (seen.contains(n)) {
                    return false;
                }
                seen.add(n); // 保存本轮整个数，下一轮检查是否重复。
                n = getNext(n);
            }
            return true;
        }

        private int getNext(int n) {
            int sum = 0;
            while (n > 0) {
                int digit = n % 10;
                sum += digit * digit;
                n /= 10;
            }
            return sum;
        }
    }
}
