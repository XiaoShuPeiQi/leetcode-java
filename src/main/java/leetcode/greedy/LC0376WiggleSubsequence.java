package leetcode.greedy;

import java.util.*;

public class LC0376WiggleSubsequence {

    public static void main(String[] args) {
        // 测试来源：飞书贪心笔记的实例走过程，2026-10-14读取。
        // https://acn581cm6pdd.feishu.cn/wiki/QHnaw0xWDiNBTfkmaG3cbydHnYf
        int[] nums = new int[]{1,2,2,3,1};
        int expected = 3;
        int actual = new Solution().wiggleMaxLength(nums);
        System.out.println("Input:");
        System.out.println("nums = [1, 2, 2, 3, 1]");
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
        public int wiggleMaxLength(int[] nums) {
            if (nums.length < 2) return nums.length;
            int preDiff = 0;
            int result = 1;
            for (int i = 1; i < nums.length; i++) {
                int curDiff = nums[i] - nums[i - 1];
                if ((curDiff > 0 && preDiff <= 0) || (curDiff < 0 && preDiff >= 0)) {
                    result++;
                    preDiff = curDiff; // 平坡、连续同向都保留上一段方向
                }
            }
            return result;
        }
    }
}
