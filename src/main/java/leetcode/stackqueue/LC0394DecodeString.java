package leetcode.stackqueue;

import java.util.ArrayDeque;
import java.util.Deque;
import common.TreeSupport;

public class LC0394DecodeString {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "s = \"3[a2[c]]\"",
                "accaccacc", runSolution());
    }

    // 测试来源：飞书 Hot100 第 71 题的实例走过程。
    static Object runSolution() {
        String s = "3[a2[c]]";
        return new Solution().decodeString(s);
    }

    static class Solution {
        public String decodeString(String s) {
            Deque<Integer> numStack = new ArrayDeque<>();    // 数字栈
            Deque<String> strStack = new ArrayDeque<>();     // 字符串栈
            StringBuilder curStr = new StringBuilder(); // 当前累积的字符串
            int num = 0;                                // 当前数字

            for (char c : s.toCharArray()) {
                if (Character.isDigit(c)) {
                    // 累积数字（可能是多位数）
                    num = num * 10 + (c - '0');
                } else if (c == '[') {
                    // 遇到 '[', 入栈，开始新的一层
                    numStack.push(num);
                    strStack.push(curStr.toString());
                    num = 0;
                    curStr = new StringBuilder();
                } else if (c == ']') {
                    // 遇到 ']', 出栈，解码当前层
                    int repeatTimes = numStack.pop();
                    String prevStr = strStack.pop();

                    // 当前字符串重复 repeatTimes 次，拼接到上一层
                    String temp = curStr.toString();
                    curStr = new StringBuilder(prevStr);
                    for (int i = 0; i < repeatTimes; i++) {
                        curStr.append(temp);
                    }
                } else {
                    // 字母，累积到当前字符串
                    curStr.append(c);
                }
            }

            return curStr.toString();
        }
    }
}
