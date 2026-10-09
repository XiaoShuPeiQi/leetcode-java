package leetcode.backtracking;

import java.util.*;

public class LC0046Permutations {

    public static void main(String[] args) {
        // 测试用例：飞书回溯笔记 3.11 的执行过程示例
        // 来源：https://acn581cm6pdd.feishu.cn/docx/VSYBdzUkuoIxf3xuSfVcsgKkn3g
        int[] nums = {1, 2, 3};
        List<List<Integer>> expected = List.of(
                List.of(1, 2, 3), List.of(1, 3, 2), List.of(2, 1, 3),
                List.of(2, 3, 1), List.of(3, 1, 2), List.of(3, 2, 1));

        System.out.println("LeetCode 46 - 全排列");
        System.out.println("Input:");
        System.out.println("nums = " + Arrays.toString(nums));

        Solution solution = new Solution();
        List<List<Integer>> actual = solution.permute(nums);

        System.out.println("Expected:");
        System.out.println(expected);
        System.out.println("Actual:");
        System.out.println(actual);
        // 仅忽略结果外部顺序，保留序列、分段或棋盘内部顺序及重复次数。
        boolean passed = resultCounts(actual).equals(resultCounts(expected));
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) {
            throw new AssertionError("全排列：实际结果与预期结果不一致");
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
        public List<List<Integer>> permute(int[] nums) {
            List<List<Integer>> result = new ArrayList<>();
            boolean[] used = new boolean[nums.length];
            List<Integer> path = new ArrayList<>();
            backtracking(nums, used, path, result);
            return result;
        }

        private void backtracking(int[] nums, boolean[] used,
                List<Integer> path, List<List<Integer>> result) {
            if (path.size() == nums.length) {
                result.add(new ArrayList<>(path));
                return;
            }
            // 每层从 0 选，used 控制路径占用。
            for (int i = 0; i < nums.length; i++) {
                if (used[i]) {
                    continue;
                }
                used[i] = true;
                path.add(nums[i]);
                backtracking(nums, used, path, result);
                path.remove(path.size() - 1);
                used[i] = false;
            }
        }
    }
}
