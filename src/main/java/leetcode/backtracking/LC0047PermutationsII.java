package leetcode.backtracking;

import java.util.*;

public class LC0047PermutationsII {

    public static void main(String[] args) {
        // 测试用例：飞书回溯笔记 3.12 的执行过程示例
        // 来源：https://acn581cm6pdd.feishu.cn/docx/VSYBdzUkuoIxf3xuSfVcsgKkn3g
        int[] nums = {1, 1, 2};
        List<List<Integer>> expected = List.of(List.of(1, 1, 2), List.of(1, 2, 1), List.of(2, 1, 1));

        System.out.println("LeetCode 47 - 全排列 II");
        System.out.println("Input:");
        System.out.println("nums = " + Arrays.toString(nums));

        Solution solution = new Solution();
        List<List<Integer>> actual = solution.permuteUnique(nums);

        System.out.println("Expected:");
        System.out.println(expected);
        System.out.println("Actual:");
        System.out.println(actual);
        // 仅忽略结果外部顺序，保留序列、分段或棋盘内部顺序及重复次数。
        boolean passed = resultCounts(actual).equals(resultCounts(expected));
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) {
            throw new AssertionError("全排列 II：实际结果与预期结果不一致");
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
        public List<List<Integer>> permuteUnique(int[] nums) {
            nums = nums.clone();
            Arrays.sort(nums);
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
            for (int i = 0; i < nums.length; i++) {
                if (used[i]) {
                    continue;
                }
                // 前一个同值未使用时，跳过重复分支。
                if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
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
