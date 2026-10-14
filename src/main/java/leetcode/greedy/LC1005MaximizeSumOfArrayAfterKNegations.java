package leetcode.greedy;

import java.util.*;

public class LC1005MaximizeSumOfArrayAfterKNegations {

    public static void main(String[] args) {
        // 测试来源：飞书贪心笔记的实例走过程，2026-10-14读取。
        // https://acn581cm6pdd.feishu.cn/wiki/QHnaw0xWDiNBTfkmaG3cbydHnYf
        int[] nums = new int[]{-4,-2,3};
        int k = 3;
        int expected = 5;
        int actual = new Solution().largestSumAfterKNegations(nums, k);
        System.out.println("Input:");
        System.out.println("nums = [-4, -2, 3], k = 3");
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
        public int largestSumAfterKNegations(int[] nums, int k) {
            nums = nums.clone();
            Arrays.sort(nums);
            for (int i = 0; i < nums.length && nums[i] < 0 && k > 0; i++) {
                nums[i] = -nums[i];
                k--;
            }
            int sum = 0;
            int minIndex = 0;
            for (int i = 0; i < nums.length; i++) {
                sum += nums[i];
                if (Math.abs(nums[i]) < Math.abs(nums[minIndex])) minIndex = i;
            }
            if (k % 2 == 1) sum -= 2 * nums[minIndex]; // 两次取反抵消，只处理余下的一次
            return sum;
        }
    }
}
