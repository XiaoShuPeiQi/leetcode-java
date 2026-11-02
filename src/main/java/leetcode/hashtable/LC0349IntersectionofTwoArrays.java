package leetcode.hashtable;

import java.util.*;

public class LC0349IntersectionofTwoArrays {

    public static void main(String[] args) {
        // 测试来源：飞书哈希表笔记的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/F4qJw6Pr2if4LkkJBjBc8aM3nXd
        int[] nums1 = new int[]{1,2,2,1};
        int[] nums2 = new int[]{2,2};
        int[] expected = new int[]{2};
        int[] actual = new Solution().intersection(nums1, nums2);
        System.out.println("Input: nums1 = [1, 2, 2, 1], nums2 = [2, 2]");
        System.out.println("Expected: " + Arrays.toString(expected));
        System.out.println("Actual: " + Arrays.toString(actual));
        boolean passed = Arrays.equals(actual, expected);
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) {
            throw new AssertionError("结果与预期不同");
        }
    }

    static class Solution {
        public int[] intersection(int[] nums1, int[] nums2) {
            Set<Integer> seen = new HashSet<>();
            Set<Integer> result = new HashSet<>();
            for (int value : nums1) {
                seen.add(value);
            }
            for (int value : nums2) {
                // seen 负责查找，result 负责答案去重。
                if (seen.contains(value)) {
                    result.add(value);
                }
            }

            int[] answer = new int[result.size()];
            int index = 0;
            for (int value : result) {
                answer[index++] = value;
            }
            return answer;
        }
    }
}
