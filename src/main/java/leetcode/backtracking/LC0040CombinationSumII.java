package leetcode.backtracking;

import java.util.*;

public class LC0040CombinationSumII {

    public static void main(String[] args) {
        // 测试用例：飞书回溯笔记 3.5 的执行过程示例
        // 来源：https://acn581cm6pdd.feishu.cn/docx/VSYBdzUkuoIxf3xuSfVcsgKkn3g
        int[] candidates = {1, 1, 2};
        int target = 2;
        List<List<Integer>> expected = List.of(List.of(1, 1), List.of(2));

        System.out.println("LeetCode 40 - 组合总和 II");
        System.out.println("Input:");
        System.out.println("candidates = " + Arrays.toString(candidates) + ", target = " + target);

        Solution solution = new Solution();
        List<List<Integer>> actual = solution.combinationSum2(candidates, target);

        System.out.println("Expected:");
        System.out.println(expected);
        System.out.println("Actual:");
        System.out.println(actual);
        // 组合内部和结果外部顺序无关；按出现次数比较，保留重复项。
        boolean passed = combinationCounts(actual).equals(combinationCounts(expected));
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) {
            throw new AssertionError("组合总和 II：实际结果与预期结果不一致");
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
        public List<List<Integer>> combinationSum2(int[] candidates, int target) {
            candidates = candidates.clone();
            Arrays.sort(candidates);
            List<List<Integer>> result = new ArrayList<>();
            List<Integer> path = new ArrayList<>();
            backtracking(candidates, target, 0, path, result);
            return result;
        }

        private void backtracking(int[] candidates, int remaining, int startIndex,
                List<Integer> path, List<List<Integer>> result) {
            if (remaining == 0) {
                result.add(new ArrayList<>(path));
                return;
            }
            for (int i = startIndex; i < candidates.length; i++) {
                // 同层去重。
                if (i > startIndex && candidates[i] == candidates[i - 1]) {
                    continue;
                }
                if (candidates[i] > remaining) {
                    break;
                }
                path.add(candidates[i]);
                // 传 i + 1：当前下标不再使用。
                backtracking(candidates, remaining - candidates[i], i + 1, path, result);
                path.remove(path.size() - 1);
            }
        }
    }
}
