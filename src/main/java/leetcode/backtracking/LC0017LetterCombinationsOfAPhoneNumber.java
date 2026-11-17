package leetcode.backtracking;

import java.util.*;

public class LC0017LetterCombinationsOfAPhoneNumber {

    public static void main(String[] args) {
        // 测试用例：飞书回溯笔记 3.3 的执行过程示例
        // 来源：https://acn581cm6pdd.feishu.cn/docx/VSYBdzUkuoIxf3xuSfVcsgKkn3g
        String digits = "23";
        List<String> expected = List.of("ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf");

        System.out.println("LeetCode 17 - 电话号码的字母组合");
        System.out.println("Input:");
        System.out.println("digits = " + digits);

        Solution solution = new Solution();
        List<String> actual = solution.letterCombinations(digits);

        System.out.println("Expected:");
        System.out.println(expected);
        System.out.println("Actual:");
        System.out.println(actual);
        // 仅忽略结果外部顺序，保留序列、分段或棋盘内部顺序及重复次数。
        boolean passed = resultCounts(actual).equals(resultCounts(expected));
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) {
            throw new AssertionError("电话号码的字母组合：实际结果与预期结果不一致");
        }
    }

    private static <T> Map<T, Integer> resultCounts(List<T> results) {
        Map<T, Integer> counts = new HashMap<>();
        for (T result : results) {
            counts.merge(result, 1, Integer::sum);
        }
        return counts;
    }

    // 飞书原始代码：只增加 static 包装与一层缩进，算法保持原样。
    static class Solution {
        private final String[] letters = {
            "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"
        };

        public List<String> letterCombinations(String digits) {
            List<String> result = new ArrayList<>();
            if (digits.isEmpty()) {
                return result;
            }
            StringBuilder path = new StringBuilder();
            backtracking(digits, 0, path, result);
            return result;
        }

        private void backtracking(String digits, int index,
                StringBuilder path, List<String> result) {
            if (index == digits.length()) {
                result.add(path.toString());
                return;
            }
            // 当前数字对应的字母。
            String choices = letters[digits.charAt(index) - '0'];
            for (int i = 0; i < choices.length(); i++) {
                path.append(choices.charAt(i));
                backtracking(digits, index + 1, path, result);
                path.deleteCharAt(path.length() - 1);
            }
        }
    }

    // Hot100 课堂使用代码随想录的 Java 实现。
    public static class Hot100Demo {
        public static void main(String[] args) {
            common.TreeSupport.check("Hot100Solution", "digits = 23",
                    "[ad, ae, af, bd, be, bf, cd, ce, cf]", String.valueOf(runSolution()));
        }

        // 测试来源：飞书 Hot100 原有课堂示例。
        static Object runSolution() {
            String digits = "23";
            return new Hot100Solution().letterCombinations(digits);
        }
    }

    static class Hot100Solution {
        private List<String> list;
        private final StringBuilder temp = new StringBuilder();

        public List<String> letterCombinations(String digits) {
            list = new ArrayList<>();
            if (digits == null || digits.isEmpty()) {
                return list;
            }
            String[] numString = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
            backtracking(digits, numString, 0);
            return list;
        }

        private void backtracking(String digits, String[] numString, int num) {
            if (num == digits.length()) {
                list.add(temp.toString());
                return;
            }

            String letters = numString[digits.charAt(num) - '0'];
            for (int i = 0; i < letters.length(); i++) {
                temp.append(letters.charAt(i));
                backtracking(digits, numString, num + 1);
                temp.deleteCharAt(temp.length() - 1);
            }
        }
    }
}
