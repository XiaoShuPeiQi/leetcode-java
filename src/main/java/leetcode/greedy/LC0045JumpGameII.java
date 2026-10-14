package leetcode.greedy;

import java.util.*;

public class LC0045JumpGameII {

    public static void main(String[] args) {
        // 测试来源：飞书贪心笔记的实例走过程，2026-10-14读取。
        // https://acn581cm6pdd.feishu.cn/wiki/QHnaw0xWDiNBTfkmaG3cbydHnYf
        int[] nums = new int[]{2,3,1,1,4};
        int expected = 2;
        int actual = new Solution().jump(nums);
        System.out.println("Input:");
        System.out.println("nums = [2, 3, 1, 1, 4]");
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
        public int jump(int[] nums) {
            int curEnd = 0;
            int nextEnd = 0;
            int steps = 0;
            for (int i = 0; i < nums.length - 1; i++) {
                nextEnd = Math.max(nextEnd, i + nums[i]);
                if (i == curEnd) {
                    steps++; // 扫完一层，才增加一步
                    curEnd = nextEnd;
                }
            }
            return steps;
        }
    }
}
