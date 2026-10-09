package leetcode.backtracking;

import java.util.*;

public class LC0077Combinations {

    public static void main(String[] args) {
        // 测试用例：飞书回溯笔记 3.1 的执行过程示例
        // 来源：https://acn581cm6pdd.feishu.cn/docx/VSYBdzUkuoIxf3xuSfVcsgKkn3g
        int n = 4;
        int k = 2;
        List<List<Integer>> expected = List.of(
                List.of(1, 2), List.of(1, 3), List.of(1, 4),
                List.of(2, 3), List.of(2, 4), List.of(3, 4));

        System.out.println("LeetCode 77 - 组合");
        System.out.println("Input:");
        System.out.println("n = " + n + ", k = " + k);

        Solution solution = new Solution();
        List<List<Integer>> actual = solution.combine(n, k);

        System.out.println("Expected:");
        System.out.println(expected);
        System.out.println("Actual:");
        System.out.println(actual);
        // 组合内部和结果外部顺序无关；按出现次数比较，保留重复项。
        boolean passed = combinationCounts(actual).equals(combinationCounts(expected));
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) {
            throw new AssertionError("组合：实际结果与预期结果不一致");
        }
    }

    private static Map<List<Integer>, Integer> combinationCounts(List<List<Integer>> combinations) {
        Map<List<Integer>, Integer> counts = new HashMap<>();
        for (List<Integer> combination : combinations) {
            List<Integer> sorted = new ArrayList<>(combination);
            Collections.sort(sorted);
            counts.merge(sorted, 1, Integer::sum);
        }
        return counts;
    }

    // 飞书原始代码：只增加 static 包装与一层缩进，算法保持原样。
    static class Solution {
        public List<List<Integer>> combine(int n, int k) {
            List<List<Integer>> result = new ArrayList<>();
            List<Integer> path = new ArrayList<>();
            backtracking(n, k, 1, path, result);
            return result;
        }

        private void backtracking(int n, int k, int startNum,
                List<Integer> path, List<List<Integer>> result) {
            if (path.size() == k) {
                result.add(new ArrayList<>(path));
                return;
            }
            int need = k - path.size();
            // 数量剪枝：后面至少留够 need 个数。
            int lastStartIndex = n - need + 1;
            for (int i = startNum; i <= lastStartIndex; i++) {
                path.add(i);
                backtracking(n, k, i + 1, path, result);
                path.remove(path.size() - 1);
            }
        }
    }
}
