package leetcode.backtracking;

import java.util.*;

public class LC0131PalindromePartitioning {

    public static void main(String[] args) {
        // 测试用例：飞书回溯笔记 3.6 的执行过程示例
        // 来源：https://acn581cm6pdd.feishu.cn/docx/VSYBdzUkuoIxf3xuSfVcsgKkn3g
        String s = "aab";
        List<List<String>> expected = List.of(List.of("a", "a", "b"), List.of("aa", "b"));

        System.out.println("LeetCode 131 - 分割回文串");
        System.out.println("Input:");
        System.out.println("s = " + s);

        Solution solution = new Solution();
        List<List<String>> actual = solution.partition(s);

        System.out.println("Expected:");
        System.out.println(expected);
        System.out.println("Actual:");
        System.out.println(actual);
        // 仅忽略结果外部顺序，保留序列、分段或棋盘内部顺序及重复次数。
        boolean passed = resultCounts(actual).equals(resultCounts(expected));
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) {
            throw new AssertionError("分割回文串：实际结果与预期结果不一致");
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
        public List<List<String>> partition(String s) {
            List<List<String>> result = new ArrayList<>();
            List<String> path = new ArrayList<>();
            backtracking(s, 0, path, result);
            return result;
        }

        private void backtracking(String s, int startIndex,
                List<String> path, List<List<String>> result) {
            if (startIndex == s.length()) {
                result.add(new ArrayList<>(path));
                return;
            }
            for (int end = startIndex; end < s.length(); end++) {
                // 当前段不是回文，换一个段尾。
                if (!isPalindrome(s, startIndex, end)) {
                    continue;
                }
                String segment = s.substring(startIndex, end + 1);
                path.add(segment);
                // 从下一段起点继续。
                backtracking(s, end + 1, path, result);
                path.remove(path.size() - 1);
            }
        }
        private boolean isPalindrome(String s, int left, int right) {
            while (left < right) {
                if (s.charAt(left) != s.charAt(right)) {
                    return false;
                }
                left++;
                right--;
            }
            return true;
        }
    }
}
