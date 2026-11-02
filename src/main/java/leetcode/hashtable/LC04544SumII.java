package leetcode.hashtable;

import java.util.*;

public class LC04544SumII {

    public static void main(String[] args) {
        // 测试来源：飞书哈希表笔记的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/F4qJw6Pr2if4LkkJBjBc8aM3nXd
        int[] nums1 = new int[]{1,2};
        int[] nums2 = new int[]{-2,-1};
        int[] nums3 = new int[]{-1,2};
        int[] nums4 = new int[]{0,2};
        int expected = 2;
        int actual = new Solution().fourSumCount(nums1, nums2, nums3, nums4);
        System.out.println("Input: nums1 = [1, 2], nums2 = [-2, -1], nums3 = [-1, 2], nums4 = [0, 2]");
        System.out.println("Expected: " + expected);
        System.out.println("Actual: " + actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) {
            throw new AssertionError("结果与预期不同");
        }
    }

    static class Solution {
        public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
            Map<Integer, Integer> count = new HashMap<>();
            for (int a : nums1) {
                for (int b : nums2) {
                    int sum = a + b;
                    count.put(sum, count.getOrDefault(sum, 0) + 1);
                }
            }

            int result = 0;
            for (int c : nums3) {
                for (int d : nums4) {
                    int need = -(c + d);
                    // 一个补数可能对应多组下标对，要累加次数。
                    result += count.getOrDefault(need, 0);
                }
            }
            return result;
        }
    }
}
