package leetcode.backtracking;

import java.util.*;
import common.TreeSupport;


public class LC0022GenerateParentheses {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "n=3",
                "[((())), (()()), (())(), ()(()), ()()()]", String.valueOf(runSolution()));
    }

    // 测试来源：力扣示例与 Hot100 笔记。
    static Object runSolution() {
        return new Solution().generateParenthesis(3);
    }

    static class Solution {
        public List<String> generateParenthesis(int n) {
            List<String> result = new ArrayList<>();
            StringBuilder path = new StringBuilder();
            backtracking(n, 0, 0, path, result);
            return result;
        }

        private void backtracking(int n, int left, int right,
                StringBuilder path, List<String> result) {
            if (right == n) {
                result.add(path.toString());
                return;
            }
            if (left < n) {
                path.append('(');
                backtracking(n, left + 1, right, path, result);
                path.deleteCharAt(path.length() - 1);
            }
            if (right < left) {
                path.append(')');
                backtracking(n, left, right + 1, path, result);
                path.deleteCharAt(path.length() - 1);
            }
        }
    }
}
