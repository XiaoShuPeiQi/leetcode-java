package leetcode.greedy;

import java.util.*;

public class LC0055JumpGame {

    public static void main(String[] args) {
        // 测试来源：飞书贪心笔记的实例走过程，2026-10-14读取。
        // https://acn581cm6pdd.feishu.cn/wiki/QHnaw0xWDiNBTfkmaG3cbydHnYf
        int[] nums = new int[]{3,2,1,0,4};
        boolean expected = false;
        boolean actual = new Solution().canJump(nums);
        System.out.println("Input:");
        System.out.println("nums = [3, 2, 1, 0, 4]");
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
        public boolean canJump(int[] nums) {
            int cover = 0;
            for (int i = 0; i < nums.length && i <= cover; i++) {
                cover = Math.max(cover, i + nums[i]);
                if (cover >= nums.length - 1) return true;
            }
            return false;
        }
    }
}
