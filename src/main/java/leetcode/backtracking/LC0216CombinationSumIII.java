package leetcode.backtracking;

import java.util.*;

public class LC0216CombinationSumIII {

    public static void main(String[] args) {
        // 测试用例：飞书回溯笔记 3.2 的执行过程示例
        // 来源：https://acn581cm6pdd.feishu.cn/docx/VSYBdzUkuoIxf3xuSfVcsgKkn3g
        int k = 3;
        int n = 7;
        List<List<Integer>> expected = List.of(List.of(1, 2, 4));

        System.out.println("LeetCode 216 - 组合总和 III");
        System.out.println("Input:");
        System.out.println("k = " + k + ", n = " + n);

        Solution solution = new Solution();
        List<List<Integer>> actual = solution.combinationSum3(k, n);

        System.out.println("Expected:");
        System.out.println(expected);
        System.out.println("Actual:");
        System.out.println(actual);
        // 组合内部和结果外部顺序无关；按出现次数比较，保留重复项。
        boolean passed = combinationCounts(actual).equals(combinationCounts(expected));
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) {
            throw new AssertionError("组合总和 III：实际结果与预期结果不一致");
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
        public List<List<Integer>> combinationSum3(int k, int n) {
            List<List<Integer>> result = new ArrayList<>();
            List<Integer> path = new ArrayList<>();
            backtracking(k, n, 1, path, result);
            return result;
        }

        private void backtracking(int k, int remaining, int startIndex,
                List<Integer> path, List<List<Integer>> result) {
            // 选满 k 个后，只检查和是否满足。
            if (path.size() == k) {
                if (remaining == 0) {
                    result.add(new ArrayList<>(path));
                }
                return;
            }
            int need = k - path.size();
            int lastStartIndex = 9 - need + 1;
            for (int i = startIndex; i <= lastStartIndex; i++) {
                // 后面的数更大，可以直接停止。
                if (i > remaining) {
                    break;
                }
                path.add(i);
                backtracking(k, remaining - i, i + 1, path, result);
                path.remove(path.size() - 1);
            }
        }
    }
}
