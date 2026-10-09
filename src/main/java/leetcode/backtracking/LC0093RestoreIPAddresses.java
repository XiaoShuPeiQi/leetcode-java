package leetcode.backtracking;

import java.util.*;

public class LC0093RestoreIPAddresses {

    public static void main(String[] args) {
        // 测试用例：飞书回溯笔记 3.7 的执行过程示例
        // 来源：https://acn581cm6pdd.feishu.cn/docx/VSYBdzUkuoIxf3xuSfVcsgKkn3g
        String s = "010010";
        List<String> expected = List.of("0.10.0.10", "0.100.1.0");

        System.out.println("LeetCode 93 - 复原 IP 地址");
        System.out.println("Input:");
        System.out.println("s = " + s);

        Solution solution = new Solution();
        List<String> actual = solution.restoreIpAddresses(s);

        System.out.println("Expected:");
        System.out.println(expected);
        System.out.println("Actual:");
        System.out.println(actual);
        // 仅忽略结果外部顺序，保留序列、分段或棋盘内部顺序及重复次数。
        boolean passed = resultCounts(actual).equals(resultCounts(expected));
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) {
            throw new AssertionError("复原 IP 地址：实际结果与预期结果不一致");
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
        public List<String> restoreIpAddresses(String s) {
            List<String> result = new ArrayList<>();
            List<String> path = new ArrayList<>();
            backtracking(s, 0, path, result);
            return result;
        }

        private void backtracking(String s, int startIndex,
                List<String> path, List<String> result) {
            // 四段后，必须恰好用完字符串。
            if (path.size() == 4) {
                if (startIndex == s.length()) {
                    result.add(String.join(".", path));
                }
                return;
            }
            int segments = 4 - path.size();
            int characters = s.length() - startIndex;
            // 剩余每段需要 1～3 个字符。
            if (characters < segments || characters > 3 * segments) {
                return;
            }
            int value = 0;
            for (int end = startIndex; end < s.length() && end < startIndex + 3; end++) {
                // 只允许单个 0，禁止前导零。
                if (end > startIndex && s.charAt(startIndex) == '0') {
                    break;
                }
                value = value * 10 + s.charAt(end) - '0';
                // 继续延长只会更大。
                if (value > 255) {
                    break;
                }
                String segment = s.substring(startIndex, end + 1);
                path.add(segment);
                backtracking(s, end + 1, path, result);
                path.remove(path.size() - 1);
            }
        }
    }
}
