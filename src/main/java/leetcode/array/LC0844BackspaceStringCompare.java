package leetcode.array;

import java.util.*;

public class LC0844BackspaceStringCompare {
    public static void main(String[] args) {
        // 来源：数组章节本次修订的实例走过程。
        String s = "ab#c";
        String t = "ad#c";
        boolean expected = true;
        boolean actual = new Solution().backspaceCompare(s, t);
        System.out.println("Input: s = \"ab#c\", t = \"ad#c\"");
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
        public boolean backspaceCompare(String s, String t) {
            int i = s.length() - 1;
            int j = t.length() - 1;
            int skipS = 0;
            int skipT = 0;
            while (i >= 0 || j >= 0) {
                while (i >= 0) { // 找s中最后一个未被退格删除的字符
                    if (s.charAt(i) == '#') {
                        skipS++;
                        i--;
                    } else if (skipS > 0) {
                        skipS--;
                        i--;
                    } else {
                        break;
                    }
                }
                while (j >= 0) { // t独立计算自己的退格次数
                    if (t.charAt(j) == '#') {
                        skipT++;
                        j--;
                    } else if (skipT > 0) {
                        skipT--;
                        j--;
                    } else {
                        break;
                    }
                }
                if (i < 0 || j < 0) {
                    return i == j; // 都耗尽才相等
                }
                if (s.charAt(i) != t.charAt(j)) {
                    return false;
                }
                i--;
                j--;
            }
            return true;
        }
    }
}
