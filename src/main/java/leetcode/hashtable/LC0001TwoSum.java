package leetcode.hashtable;

import java.util.*;

public class LC0001TwoSum {

    public static void main(String[] args) {
        // 测试来源：飞书哈希表笔记的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/F4qJw6Pr2if4LkkJBjBc8aM3nXd
        int[] nums = new int[]{2,7,11,15};
        int target = 9;
        int[] expected = new int[]{0,1};
        int[] actual = new Solution().twoSum(nums, target);
        System.out.println("Input: nums = [2, 7, 11, 15], target = 9");
        System.out.println("Expected: " + Arrays.toString(expected));
        System.out.println("Actual: " + Arrays.toString(actual));
        boolean passed = Arrays.equals(actual, expected);
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) {
            throw new AssertionError("结果与预期不同");
        }
    }

    static class Solution {
        public int[] twoSum(int[] nums, int target) {
            int[] res = new int[2]; // res[0]和res[1]分别保存两个数的下标
            Map<Integer, Integer> map = new HashMap<>();

            for (int i = 0; i < nums.length; i++) {
                int temp = target - nums[i];
                if (map.containsKey(temp)) {
                    res[0] = map.get(temp); // 之前那个数的下标
                    res[1] = i;            // 当前数的下标
                } else {
                    map.put(nums[i], i); // 没找到配对时，记录当前数供后面查询
                }
            }

            return res;
        }
    }

    // 保留此前版本，便于对照；main和Hot100调试器使用上方的清晰写法。
    static class SolutionOriginal {
        public int[] twoSum(int[] nums, int target) {
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                int need = target - nums[i];
                // 此时 map 只含之前的元素，不会重复使用当前下标。
                if (map.containsKey(need)) {
                    return new int[]{map.get(need), i};
                }
                map.put(nums[i], i);
            }
            return new int[0];
        }
    }
}
