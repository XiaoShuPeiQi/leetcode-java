package leetcode.greedy;

import java.util.*;

public class LC0053MaximumSubarray {

    public static void main(String[] args) {
        // 测试来源：飞书贪心笔记的实例走过程，2026-10-14读取。
        // https://acn581cm6pdd.feishu.cn/wiki/QHnaw0xWDiNBTfkmaG3cbydHnYf
        int[] nums = new int[]{-2,1,-3,4,-1,2,1};
        int expected = 6;
        int actual = new Solution().maxSubArray(nums);
        System.out.println("Input:");
        System.out.println("nums = [-2, 1, -3, 4, -1, 2, 1]");
        System.out.println("Expected:");
        System.out.println(expected);
        System.out.println("Actual:");
        System.out.println(actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) throw new AssertionError("结果与预期不同");
    }

    // 保留飞书当前Solution类体，只增加static和一层缩进。
    static class Solution {
        public int maxSubArray(int[] nums) {
            int sum = 0;
            int result = nums[0];
            for (int num : nums) {
                sum += num;
                result = Math.max(result, sum);
                if (sum < 0) sum = 0; // 负前缀不带到下一段
            }
            return result;
        }
    }
}
