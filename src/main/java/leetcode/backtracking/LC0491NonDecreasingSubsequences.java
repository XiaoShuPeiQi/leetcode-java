package leetcode.backtracking;

import java.util.*;

public class LC0491NonDecreasingSubsequences {

    public static void main(String[] args) {
        // 测试用例：飞书回溯笔记 3.10 的执行过程示例
        // 来源：https://acn581cm6pdd.feishu.cn/docx/VSYBdzUkuoIxf3xuSfVcsgKkn3g
        int[] nums = {4, 7, 6, 7};
        // 原文演示部分分支；以下按同一输入的题意列出完整预期结果。
        List<List<Integer>> expected = List.of(
                List.of(4, 7), List.of(4, 7, 7), List.of(4, 6),
                List.of(4, 6, 7), List.of(7, 7), List.of(6, 7));

        System.out.println("LeetCode 491 - 非递减子序列");
        System.out.println("Input:");
        System.out.println("nums = " + Arrays.toString(nums));

        Solution solution = new Solution();
        List<List<Integer>> actual = solution.findSubsequences(nums);

        System.out.println("Expected:");
        System.out.println(expected);
        System.out.println("Actual:");
        System.out.println(actual);
        // 仅忽略结果外部顺序，保留序列、分段或棋盘内部顺序及重复次数。
        boolean passed = resultCounts(actual).equals(resultCounts(expected));
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) {
            throw new AssertionError("非递减子序列：实际结果与预期结果不一致");
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
        public List<List<Integer>> findSubsequences(int[] nums) {
            List<List<Integer>> result = new ArrayList<>();
            List<Integer> path = new ArrayList<>();
            backtracking(nums, 0, path, result);
            return result;
        }

        private void backtracking(int[] nums, int startIndex,
                List<Integer> path, List<List<Integer>> result) {
            // 达标就收集，仍继续找更长序列。
            if (path.size() >= 2) {
                result.add(new ArrayList<>(path));
            }
            // 每层独立记录选过的值。
            Set<Integer> levelUsed = new HashSet<>();
            for (int i = startIndex; i < nums.length; i++) {
                if (!path.isEmpty() && nums[i] < path.get(path.size() - 1)) {
                    continue;
                }
                // 同层同值只展开一次。
                if (!levelUsed.add(nums[i])) {
                    continue;
                }
                path.add(nums[i]);
                backtracking(nums, i + 1, path, result);
                path.remove(path.size() - 1);
            }
        }
    }
}
