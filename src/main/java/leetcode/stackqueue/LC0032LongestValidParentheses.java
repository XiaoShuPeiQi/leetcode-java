package leetcode.stackqueue;

import common.TreeSupport;
import java.util.ArrayDeque;
import java.util.Deque;

public class LC0032LongestValidParentheses {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "s = \")()())\"",
                4, runSolution());
    }

    // 力扣官方示例，与本节推演一致。
    static Object runSolution() {
        String s = ")()())";
        return new Solution().longestValidParentheses(s);
    }

    static class Solution {
        public int longestValidParentheses(String s) {
            Deque<Integer> stack = new ArrayDeque<>();
            stack.push(-1);
            int result = 0;

            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '(') {
                    stack.push(i);
                } else {
                    stack.pop();
                    if (stack.isEmpty()) {
                        stack.push(i);
                    } else {
                        result = Math.max(result, i - stack.peek());
                    }
                }
            }
            return result;
        }
    }
}
