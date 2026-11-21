package leetcode.stackqueue;

import java.util.ArrayDeque;
import java.util.Deque;
import common.TreeSupport;

public class LC0020ValidParentheses {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "s = \"{[]}\"",
                true, runSolution());
    }

    // 测试来源：飞书 Hot100 第 69 题的实例走过程。
    static Object runSolution() {
        String s = "{[]}";
        return new Solution().isValid(s);
    }

    static class Solution {
        public boolean isValid(String s) {
            Deque<Character> stack = new ArrayDeque<>();

            for (char c : s.toCharArray()) {
                if (c == '(') {
                    // 压入期待的右括号，栈顶表示下一步应匹配谁。
                    stack.push(')');
                } else if (c == '[') {
                    stack.push(']');
                } else if (c == '{') {
                    stack.push('}');
                } else {
                    // 右括号多余或类型不符，立即失败。
                    if (stack.isEmpty() || stack.peek() != c) {
                        return false;
                    }

                    stack.pop();
                }
            }

            // 遍历结束还留有期待的括号，说明左括号多余。
            return stack.isEmpty();
        }
    }
}
