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
