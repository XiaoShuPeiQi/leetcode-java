package leetcode.greedy;

import java.util.*;

public class LC0738MonotoneIncreasingDigits {

    public static void main(String[] args) {
        // 测试来源：飞书贪心笔记的实例走过程，2026-10-14读取。
        // https://acn581cm6pdd.feishu.cn/wiki/QHnaw0xWDiNBTfkmaG3cbydHnYf
        int n = 332;
        int expected = 299;
        int actual = new Solution().monotoneIncreasingDigits(n);
        System.out.println("Input:");
        System.out.println("n = 332");
        System.out.println("Expected:");
        System.out.println(expected);
        System.out.println("Actual:");
        System.out.println(actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) throw new AssertionError("结果与预期不同");
    }

    // 保留飞书当前Solution类体，只增加static和一层缩进。
    static class Solution {
        public int monotoneIncreasingDigits(int n) {
            char[] digits = String.valueOf(n).toCharArray();
            int mark = digits.length;
            for (int i = digits.length - 1; i > 0; i--) {
                if (digits[i - 1] > digits[i]) {
                    digits[i - 1]--;
                    mark = i; // 更左的回退会覆盖更长后缀
                }
            }
            for (int i = mark; i < digits.length; i++) digits[i] = '9';
            return Integer.parseInt(new String(digits));
        }
    }
}
