package leetcode.backtracking;

import java.util.*;

public class LC0090SubsetsII {

    public static void main(String[] args) {
        // 测试用例：飞书回溯笔记 3.9 的执行过程示例
        // 来源：https://acn581cm6pdd.feishu.cn/docx/VSYBdzUkuoIxf3xuSfVcsgKkn3g
        int[] nums = {1, 2, 2};
        List<List<Integer>> expected = List.of(
                List.of(), List.of(1), List.of(1, 2), List.of(1, 2, 2), List.of(2), List.of(2, 2));

        System.out.println("LeetCode 90 - 子集 II");
        System.out.println("Input:");
        System.out.println("nums = " + Arrays.toString(nums));

        Solution solution = new Solution();
        List<List<Integer>> actual = solution.subsetsWithDup(nums);

        System.out.println("Expected:");
        System.out.println(expected);
        System.out.println("Actual:");
        System.out.println(actual);
        // 组合内部和结果外部顺序无关；按出现次数比较，保留重复项。
        boolean passed = combinationCounts(actual).equals(combinationCounts(expected));
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) {
            throw new AssertionError("子集 II：实际结果与预期结果不一致");
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
        public List<List<Integer>> subsetsWithDup(int[] nums) {
            nums = nums.clone();
            Arrays.sort(nums);
            List<List<Integer>> result = new ArrayList<>();
            List<Integer> path = new ArrayList<>();
            backtracking(nums, 0, path, result);
            return result;
        }

        private void backtracking(int[] nums, int startIndex,
                List<Integer> path, List<List<Integer>> result) {
            result.add(new ArrayList<>(path));
            for (int i = startIndex; i < nums.length; i++) {
                // 同层去重。
                if (i > startIndex && nums[i] == nums[i - 1]) {
                    continue;
                }
                path.add(nums[i]);
                backtracking(nums, i + 1, path, result);
                path.remove(path.size() - 1);
            }
        }
    }
}
